<template>
  <div class="page-container">
    <div class="form-container">
      <h1 style="text-align: center">员工管理系统</h1>
      <a-form
          :model="loginForm"
          name="basic"
          :label-col="{ span: 6 }"
          :wrapper-col="{ span: 14 }"
          autocomplete="off"
          @finish="login"
          @finishFailed="loginFailed"
      >
        <a-form-item
            label="员工号"
            name="jobNumber"
            :rules="[{  message: '请输入员工号！' }]"
        >
          <a-input v-model:value="loginForm.jobNumber" />
        </a-form-item>

        <a-form-item
            label="密码"
            name="password"
            :rules="[{  message: 'Please input your password!' }]"
        >
          <a-input-password v-model:value="loginForm.password" />
        </a-form-item>

        <a-form-item label="类型">
          <a-radio-group v-model:value="userType.resource">
            <a-radio value="1">员工</a-radio>
            <a-radio value="2">管理者</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item :wrapper-col="{ offset: 10, span: 10 }">
          <a-button type="primary" html-type="submit">登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script>
import { defineComponent, reactive } from 'vue';
import { notification } from 'ant-design-vue';
import { useRouter } from "vue-router";
import store from "@/store";
import axios from 'axios';
export default defineComponent({
  setup() {
    const loginForm = reactive({
      jobNumber: '',
      password: '',
    });

    const userType = reactive({
      resource: '1',
    });

    const router = useRouter();
    const login = () => {
      axios.post('staff/login',{
        jobNumber: loginForm.jobNumber,
        password: loginForm.password,
        loginType: userType.resource
      }).then(response => {
        let data=response.data;
        if (data.success) {
          const { token, role, staffName } = data.content

          store.commit('setJobNumber', loginForm.jobNumber)
          store.commit('setRole', role)
          store.commit('setToken', token)
          store.commit('setStaffName', staffName)



          notification.success({ description: '登录成功', duration: 3 })

          if (role === '2') {
            window.location.href = 'http://localhost:8080/manage/homePage'
          } else {
            router.push('/home')
          }
        }else {
          notification.error({description: data.message});
        }
      })

    };

    const loginFailed = errorInfo => {
      console.log('Failed:', errorInfo);
    };
    return {
      loginForm,
      login,
      loginFailed,
      userType,

    };
  },
});
</script>



<style scoped>
.page-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center;     /* 垂直居中 */
  height: 100vh;           /* 占满整个页面高度 */
  background: none;     /* 背景色（可选） */
}

.form-container {
  border: 1px solid #1c1b1b;
  border-radius: 8px;
  padding: 24px;
  background: none;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  width: 400px; /* 固定宽度（可调） */
}

</style>