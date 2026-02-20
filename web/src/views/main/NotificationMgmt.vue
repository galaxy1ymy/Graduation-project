<template>
  <div>
    <h1>通知</h1>
    <a-list
        bordered
        :data-source="notifications"
        locale="{ emptyText: '暂无通知' }"
    >
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta :title="item.title" :description="item.content" />
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

const notifications = ref([]);
let intervalId = null;

// 获取通知列表
const fetchNotifications = async () => {
  try {
    const res = await axios.get('/manager/notifications');
    // 倒序排列：最新发布的在最上面
    notifications.value = res.data.slice().reverse();
  } catch (error) {
    console.error('获取通知列表失败', error);
  }
};

// 初始化加载 & 自动刷新
onMounted(() => {
  fetchNotifications(); // 先加载一次
  intervalId = setInterval(fetchNotifications, 10000); // 每10秒刷新一次
});

// 组件卸载时清理定时器
onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>