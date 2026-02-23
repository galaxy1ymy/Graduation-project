<template>
  <div class="my-info-page">
    <a-card title="我的信息" style="max-width: 700px; margin: 0 auto;">
      <a-form
          :model="form"
          :label-col="{ span: 6 }"
          :wrapper-col="{ span: 14 }"
      >
        <a-form-item label="工号">
          <a-input v-model:value="form.jobNumber" disabled />
        </a-form-item>

        <a-form-item label="姓名">
          <a-input v-model:value="form.name" />
        </a-form-item>

        <a-form-item label="性别">
          <a-select v-model:value="form.gender">
            <a-select-option value="男">男</a-select-option>
            <a-select-option value="女">女</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item label="年龄">
          <a-input-number v-model:value="form.age" style="width:100%" />
        </a-form-item>

        <a-form-item label="手机号">
          <a-input v-model:value="form.phone" />
        </a-form-item>

        <a-form-item label="邮箱">
          <a-input v-model:value="form.email" />
        </a-form-item>

        <a-form-item label="地址">
          <a-input v-model:value="form.address" />
        </a-form-item>

        <a-form-item :wrapper-col="{ offset: 6 }">
          <a-button type="primary" @click="saveInfo">
            保存修改
          </a-button>
        </a-form-item>

      </a-form>
    </a-card>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { message } from 'ant-design-vue'
import axios from 'axios'

// 从 store 中获取数据
const store = useStore()

const form = reactive({
  id: null,
  jobNumber: store.state.jobNumber,  // 从 Vuex 获取 jobNumber
  name: '',
  gender: '',
  age: null,
  phone: '',
  email: '',
  address: ''
})

// 获取当前员工信息
const fetchMyInfo = async () => {
  const jobNumber = store.state.jobNumber

  // 如果没有获取到 jobNumber，说明未登录
  if (!jobNumber) {
    message.error("未登录")
    return
  }

  try {
    const res = await axios.get('/staff/people/me', {
      headers: { jobNumber }
    })

    // 获取个人信息成功
    if (res.data.content) {
      form.id = res.data.content.id
      form.jobNumber = res.data.content.jobNumber
      form.name = res.data.content.name
      form.gender = res.data.content.gender
      form.age = res.data.content.age
      form.phone = res.data.content.phone
      form.email = res.data.content.email
      form.address = res.data.content.address
    } else {
      message.error("获取个人信息失败")
    }
  } catch (error) {
    message.error("获取个人信息失败")
  }
}

// 保存修改
const saveInfo = async () => {
  try {
    await axios.put(`/staff/people/${form.id}`, form)
    message.success("修改成功")
  } catch (error) {
    message.error("修改失败")
  }
}

// 在组件挂载时调用 fetchMyInfo 获取信息
onMounted(fetchMyInfo)
</script>

<style scoped>
.my-info-page {
  padding: 30px;
}
</style>