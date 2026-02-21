<template>
  <div class="department-page">
    <a-button type="primary" @click="openModal()">新建部门</a-button>
    <a-table
        :columns="columns"
        :data-source="departments"
        row-key="id"
        :loading="loading"
    >
      <template #action="{ record }">
        <a-button type="link" @click="editDepartment(record)">编辑</a-button>
        <a-button type="link" danger @click="deleteDepartment(record.id)">删除</a-button>
        <a-button type="link" @click="viewStaff(record.name)">查看员工</a-button>
      </template>
    </a-table>

    <!-- 新建/编辑部门 Modal -->
    <a-modal
        v-model:visible="modalVisible"
        :title="modalTitle"
        ok-text="确定"
        cancel-text="取消"
        @ok="submitForm"
        @cancel="closeModal"
    >
      <a-form :model="form">
        <a-form-item label="部门名称" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }">
          <a-input v-model:value="form.name" />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 查看员工 Modal -->
    <a-modal
        v-model:visible="staffModalVisible"
        title="部门员工"
        :footer="null"
        width="600px"
        @cancel="closeStaffModal"
    >
      <a-table
          :columns="staffColumns"
          :data-source="staffList"
          row-key="id"
          :pagination="{ pageSize: 5 }"
      >
      </a-table>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { message, Modal } from 'ant-design-vue'
import axios from 'axios'

const departments = ref([])
const loading = ref(false)
const modalVisible = ref(false)
const modalTitle = ref('新建部门')
const form = ref({ id: null, name: '' })

const staffModalVisible = ref(false)
const staffList = ref([])

const columns = [
  { title: '序号', dataIndex: 'id', key: 'id' },
  { title: '部门名称', dataIndex: 'name', key: 'name' },
  { title: '创建时间', dataIndex: 'createTime', key: 'createTime' },
  { title: '更新时间', dataIndex: 'updateTime', key: 'updateTime' },
  {
    title: '操作',
    key: 'action',
    slots: { customRender: 'action' }
  }
]

const staffColumns = [
  { title: '工号', dataIndex: 'jobNumber', key: 'jobNumber' },
  { title: '姓名', dataIndex: 'name', key: 'name' },
  { title: '性别', dataIndex: 'gender', key: 'gender' },
  { title: '年龄', dataIndex: 'age', key: 'age' },
  { title: '手机号', dataIndex: 'phone', key: 'phone' },
  { title: '职位', dataIndex: 'position', key: 'position' }
]

// 获取部门列表
const fetchDepartments = async () => {
  loading.value = true
  try {
    const res = await axios.get('/manager/departments')
    departments.value = res.data
  } catch (error) {
    message.error('获取部门列表失败')
  } finally {
    loading.value = false
  }
}

const openModal = () => {
  modalTitle.value = '新建部门'
  form.value = { id: null, name: '' }
  modalVisible.value = true
}

const editDepartment = (record) => {
  modalTitle.value = '编辑部门'
  form.value = { ...record }
  modalVisible.value = true
}

const closeModal = () => {
  modalVisible.value = false
}

const submitForm = async () => {
  try {
    if (form.value.id) {
      await axios.put(`/manager/departments/${form.value.id}`, form.value)
      message.success('更新成功')
    } else {
      await axios.post('/manager/departments', form.value)
      message.success('创建成功')
    }
    modalVisible.value = false
    fetchDepartments()
  } catch (error) {
    message.error('操作失败')
  }
}

const deleteDepartment = async (id) => {
  Modal.confirm({
    title: '确认删除该部门吗？',
    okText: '确定',
    cancelText: '取消',
    onOk: async () => {
      try {
        await axios.delete(`/manager/departments/${id}`)
        message.success('删除成功')
        fetchDepartments()
      } catch (error) {
        message.error('删除失败')
      }
    }
  })
}

// 查看该部门员工列表
const viewStaff = async (departmentName) => {
  try {
    const res = await axios.get('/staff/people/by-department', {
      params: { department: departmentName }
    })

    staffList.value = res.data
    staffModalVisible.value = true

  } catch (error) {
    message.error('查询部门员工失败')
  }
}

const closeStaffModal = () => {
  staffModalVisible.value = false
}

onMounted(fetchDepartments)
</script>

<style scoped>
.department-page {
  padding: 20px;
}
</style>