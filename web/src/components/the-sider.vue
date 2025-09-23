<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys2"
        v-model:openKeys="openKeys"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
        <a-menu-item key="/homePage">
          <router-link to="/homePage" style="color: inherit;">
            <home-outlined />&nbsp; 首页
          </router-link>
        </a-menu-item>
        <a-menu-item key="/myAttendance">
          <router-link to="/myAttendance" style="color: inherit;">
            <carry-out-outlined />&nbsp; 我的考勤
          </router-link>
        </a-menu-item>
        <a-menu-item key="/leaveRequest">
          <router-link to="/leaveRequest" style="color: inherit;">
            <coffee-outlined />&nbsp; 请假申请
          </router-link>
        </a-menu-item>
        <a-menu-item key="/salaryMgmt">
          <router-link to="/salaryMgmt" style="color: inherit;">
            <account-book-outlined />&nbsp; 薪资管理
          </router-link>
        </a-menu-item>
        <a-menu-item key="/notion">
          <router-link to="/notion" style="color: inherit;">
            <comment-outlined />&nbsp; 通知与会议
          </router-link>
        </a-menu-item>
        <a-menu-item key="/divisionMgmt">
          <router-link to="/divisionMgmt" style="color: inherit;">
            <usergroup-add-outlined />&nbsp; 部门管理
          </router-link>
        </a-menu-item>
    </a-menu>
  </a-layout-sider>

</template>

<script>
import { defineComponent,ref,watch} from 'vue';
import router from "@/router";
export default defineComponent({
  name: "the-sider-view",
  setup() {

    const selectedKeys = ref([]);
    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch',newValue);
      selectedKeys.value=[];
      selectedKeys.value.push(newValue);
    }, { immediate: true });

    return{
      selectedKeys,

    };
  }

})

</script>


<style scoped>

</style>