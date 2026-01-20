<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys2"
        v-model:openKeys="openKeys"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="homePage">
        <router-link :to="{ name: 'HomePage' }" style="color: inherit;">
          <home-outlined />&nbsp; 首页
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