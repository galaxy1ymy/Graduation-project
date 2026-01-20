<template>
  <a-layout-header class="header">
    <div class="logo" style="color: white">管理员系统</div>
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


    return{
      selectedKeys,

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