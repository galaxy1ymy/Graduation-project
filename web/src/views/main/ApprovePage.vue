<template>
  <a-card title="假勤">
    <a-card-grid v-for="(item, index) in menuList1" :key="index" class="grid-item">
      <a-button class="full-btn" @click="showModal">
        <component :is="item.icon" />
        <div>{{ item.label }}</div>
      </a-button>
      <a-modal v-model:visible="visible" title="Title" @ok="handleOk">
        <template #footer>
          <a-button key="back" @click="handleCancel">Return</a-button>
          <a-button key="submit" type="primary" :loading="loading" @click="handleOk">Submit</a-button>
        </template>
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Some contents...</p>
      </a-modal>
    </a-card-grid>
  </a-card>
  <br>
  <a-card title="行政">
    <a-card-grid v-for="(item, index) in menuList2" :key="index" class="grid-item">
      <a-button class="full-btn" @click="item.onClick">
        <component :is="item.icon" />
        <div>{{ item.label }}</div>
      </a-button>
    </a-card-grid>
  </a-card>
</template>
<script>
import { defineComponent, ref } from 'vue';
import { ScheduleOutlined, CarOutlined, ClockCircleOutlined, ShoppingOutlined,DesktopOutlined,ToolOutlined} from '@ant-design/icons-vue'
export default defineComponent({
  setup() {
    const loading = ref(false);
    const visible = ref(false);
    const showModal = () => {
      visible.value = true;
    };
    const handleOk = () => {
      loading.value = true;
      setTimeout(() => {
        loading.value = false;
        visible.value = false;
      }, 2000);
    };
    const handleCancel = () => {
      visible.value = false;
    };

    const menuList1 = [
      { label: '请假', icon: ScheduleOutlined},
      { label: '出差', icon: CarOutlined},
      { label: '加班', icon: ClockCircleOutlined},
      { label: '外出', icon: ShoppingOutlined}
    ]
    const menuList2 = [
      { label: '会议室预定', icon: DesktopOutlined},
      { label: '物品领取', icon: ToolOutlined},
    ]
    return {
      loading,
      visible,
      showModal,
      handleOk,
      handleCancel,
      menuList1,
      menuList2
    };
  },
});
</script>

<style scoped>
.grid-item {
  width: 25%;
  padding: 0 !important;        /* 去掉默认内边距 */
  text-align: center;
  box-sizing: border-box;
  border: 0.5px solid #e1e1e1;
}

.full-btn {
  width: 100%;
  height: 100%;
  border: none;
  border-radius: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #fff;
  transition: all 0.3s;
}

/* 悬浮效果 */
.full-btn:hover {
  background: #f0f5ff;
  color: #1677ff;
}

/* 图标大小和间距 */
.full-btn .anticon {
  font-size: 22px;
  margin-bottom: 6px;
}

</style>