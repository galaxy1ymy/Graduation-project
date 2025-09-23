<template>
  <div>欢迎您，{{ staffName }}</div>
</template>

<script>
import { defineComponent, computed } from 'vue';
import { useStore } from 'vuex';

export default defineComponent({
  setup() {
    const store = useStore();
    const staffName = computed(() => store.getters.getStaffName);
    return {
      staffName
    };
  }

});
</script>


<style scoped>

</style>