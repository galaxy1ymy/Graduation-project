<template>
  <h1>用户列表</h1>
  <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">添加用户</a-button>
  <a-input-search
      v-model:value="value"
      placeholder="用户名"
      enter-button
      @search="search"
  />

  <div style="height: 420px">
    <a-table
        :columns="columns"
        :data-source="data"
        row-key="id"
        :scroll="{ x: 1500, y: 300 }"
        bordered
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'operation'">
          <a @click="edit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
              title="确定要删除该员工吗？"
              ok-text="确定"
              cancel-text="取消"
              @confirm="remove(record)"
          >
            <a style="color: red">删除</a>
          </a-popconfirm>
        </template>
      </template>

    </a-table>
  </div>

  <a-modal
      v-model:open="visible"
      :title="formState.id ? '编辑员工' : '新增员工'"
      @ok="submitEdit"
      ok-text="保存"
      cancel-text="取消"
  >

  <a-form :model="formState" label-col="{ span: 5 }" wrapper-col="{ span: 18 }">
      <a-form-item label="姓名">
        <a-input v-model:value="formState.name" />
      </a-form-item>

      <a-form-item label="性别">
        <a-select v-model:value="formState.gender">
          <a-select-option value="男">男</a-select-option>
          <a-select-option value="女">女</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="部门">
        <a-select v-model:value="formState.department" >
          <a-select-option value="会计部门">会计部门</a-select-option>
          <a-select-option value="人事部门">人事部门</a-select-option>
          <a-select-option value="运营部门">运营部门</a-select-option>
          <a-select-option value="研发部门">研发部门</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="职位">
        <a-input v-model:value="formState.position" />
      </a-form-item>

      <a-form-item label="手机号">
        <a-input v-model:value="formState.phone" />
      </a-form-item>

      <a-form-item label="地址">
        <a-input v-model:value="formState.address" />
      </a-form-item>

      <a-form-item label="权限">
        <a-select v-model:value="formState.role" >
          <a-select-option value="1">1</a-select-option>
          <a-select-option value="员工">2</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="是否在职">
        <a-select v-model:value="formState.status" >
          <a-select-option value="是">1</a-select-option>
          <a-select-option value="否">0</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>


</template>

<script>
import { defineComponent, onMounted, ref, reactive} from 'vue'
import request from '@/utils/request'
import dayjs from 'dayjs'
import { message } from 'ant-design-vue'

export default defineComponent({
  setup() {
    const columns = [
      {
        title: '员工号',
        dataIndex: 'jobNumber',
        key: 'jobNumber',
        fixed: 'left',
        width: 100
      },
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
        fixed: 'left',
        width: 120
      },
      {
        title: '性别',
        dataIndex: 'gender',
        key: 'gender',
        width: 80
      },
      {
        title: '年龄',
        dataIndex: 'age',
        key: 'age',
        width: 80
      },
      {
        title: '部门',
        dataIndex: 'department',
        key: 'department',
        width: 120
      },
      {
        title: '职位',
        dataIndex: 'position',
        key: 'position',
        width: 150
      },
      {
        title: '手机号',
        dataIndex: 'phone',
        key: 'phone',
        width: 140
      },
      {
        title: '地址',
        dataIndex: 'address',
        key: 'address',
        width: 200
      },
      {
        title: '是否在职',
        dataIndex: 'status',
        key: 'status',
        width: 100
      },
      {
        title: '权限',
        dataIndex: 'role',
        key: 'role',
        width: 100
      },
      {
        title: '入职日期',
        dataIndex: 'hireDate',
        key: 'hireDate',
        width: 100,
        customRender: ({ text }) => {
          return text ? dayjs(text).format('YYYY-MM-DD') : ''
        }
      },
      {
        title: '操作',
        key: 'operation',
        fixed: 'right',
        width: 120
      }
    ]

    const data = ref([])

    const visible = ref(false)

    const value = ref('')

    const search = async () => {
      const res = await request.get('/staff/list', {
        params: {
          name: value.value
        }
      })
      data.value = res.content
    }


    /** 查询员工列表 */
    const loadData = async () => {
      const res = await request.get('/staff/list')
      data.value = res.content

    }


    const formState = reactive({
      id: null,
      jobNumber: '',
      name: '',
      gender: '',
      age: null,
      department: '',
      position: '',
      phone: '',
      address: '',
      role:'',
      status:''
    })


    const edit = (record) => {
      Object.assign(formState, record)
      visible.value = true
    }


    const remove = async (record) => {
      try {
        await request.delete(`/staff/${record.id}`)
        message.success('删除成功')
        loadData() // 重新加载列表
      } catch (e) {
        message.error('删除失败')
      }
    }

    const submitEdit = async () => {
      try {
        if (formState.id) {
          // 编辑员工
          await request.put(`/staff/${formState.id}`, {
            jobNumber: formState.jobNumber,
            name: formState.name,
            gender: formState.gender,
            age: formState.age,
            department: formState.department,
            position: formState.position,
            phone: formState.phone,
            address: formState.address,
            role: formState.role,
            status: formState.status
          })
          message.success('修改成功')
        } else {
          // 新增员工
          await request.post('/staff', {
            jobNumber: formState.jobNumber,
            name: formState.name,
            gender: formState.gender,
            age: formState.age,
            department: formState.department,
            position: formState.position,
            phone: formState.phone,
            address: formState.address,
            role: formState.role,
            status: formState.status
          })
          message.success('新增成功')
        }

        visible.value = false
        loadData() // 刷新表格
      } catch (e) {
        message.error('操作失败')
      }
    }

    const handleAdd = () => {
      Object.assign(formState, {
        id: null,
        jobNumber: '',
        name: '',
        gender: '',
        age: null,
        department: '',
        position: '',
        phone: '',
        address: '',
        role: '',
        status: ''
      })
      visible.value = true
    }





    onMounted(() => {
      loadData()
    })

    return {
      columns,
      data,
      visible,
      formState,
      edit,
      remove,
      submitEdit,
      loadData,
      value,
      handleAdd,
      search
    }

  }
})
</script>


<style scoped>
:deep(.ant-table-cell) {
  color: #1677ff; /* Ant Design 默认蓝 */
}
</style>
