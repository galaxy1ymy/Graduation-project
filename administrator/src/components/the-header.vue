<template>
  <a-layout-header class="header">
    <div class="logo" style="color: white">管理员系统</div>
    <div style="float: right; color: white">
      <a @click="logout" style="color: white; cursor: pointer">退出登录</a>
    </div>

    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="homePage">
        <router-link :to="{ name: 'HomePage' }" style="color: inherit;">
          <home-outlined />&nbsp; 首页
        </router-link>
      </a-menu-item>
      <a-menu-item key="staffList">
        <router-link :to="{ name: 'StaffList' }" style="color: inherit;">
          <home-outlined />&nbsp; 员工管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="leave-approval">
        <router-link :to="{ name: 'LeaveApprovalManagement' }" style="color: inherit;">
          <home-outlined />&nbsp; 请假审批管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="noticeManagement">
        <router-link :to="{ name: 'NoticeManagement' }" style="color: inherit;">
          <home-outlined />&nbsp; 通知管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="departmentManagement">
        <router-link :to="{ name: 'DepartmentManagement' }" style="color: inherit;">
          <home-outlined />&nbsp; 部门管理
        </router-link>
      </a-menu-item>

    </a-menu>
  </a-layout-header>

</template>

<script>
import { defineComponent,watch,ref} from 'vue';
import router from "@/router";
export default defineComponent({
  name: "the-header-view",
  setup() {

    const selectedKeys = ref([]);
    watch(
        () => router.currentRoute.value.name,
        (newValue) => {
          selectedKeys.value = [newValue];
        },
        { immediate: true }
    );

    const logout = () => {
      // 1️⃣ 清空登录信息（很重要）
      sessionStorage.clear()
      localStorage.clear()

      // 2️⃣ 跳转到 8081 的登录页
      window.location.href = 'http://localhost:8080/login'
    }


    return{
      selectedKeys,
      logout

    };
  }

})


</script>


<style scoped>
.logo {
  float: left;
  width: 150px;
  height: 31px;
  color: white;
  font-size: 20px;

}

</style>