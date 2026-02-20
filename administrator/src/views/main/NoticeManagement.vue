<template>
  <div>
    <a-button type="primary" @click="openModal()">发布通知</a-button>

    <a-table :data-source="notifications" row-key="id">
      <a-table-column title="标题" dataIndex="title" />
      <a-table-column title="内容" dataIndex="content" />
      <a-table-column title="操作">
        <template #default="{ record }">
          <a-button type="link" @click="openModal(record)">编辑</a-button>
          <a-button type="link" danger @click="deleteNotification(record.id)">删除</a-button>
        </template>
      </a-table-column>
    </a-table>

    <a-modal
        v-model:open="isModalVisible"
        :title="editingNotification ? '编辑通知' : '发布通知'"
        @ok="submitNotification"
        @cancel="closeModal"
        ok-text="提交"
        cancel-text="取消"
    >
      <a-form layout="vertical">
        <a-form-item label="标题">
          <a-input v-model:value="form.title" placeholder="请输入通知标题" />
        </a-form-item>
        <a-form-item label="内容">
          <a-textarea v-model:value="form.content" :rows="4" placeholder="请输入通知内容" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import request from '@/utils/request'; // 你的封装 axios

const notifications = ref([]);
const isModalVisible = ref(false);
const editingNotification = ref(null);
const form = reactive({ title: '', content: '' });

// 获取通知列表
// 获取通知列表
const fetchNotifications = async () => {
  try {
    let data = await request.get('/manager/notifications');

    // 假设每条通知有 createdAt 字段，按时间倒序
    /*notifications.value = data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));*/

    // 如果接口已经是数组倒序返回，也可以直接用 reverse()
    notifications.value = data.reverse();

  } catch (err) {
    console.error('获取通知列表失败', err);
  }
};

// 打开弹窗
const openModal = async (record = null) => {
  if (record) {
    try {
      const data = await request.get(`/manager/notifications/${record.id}`);
      editingNotification.value = { ...data };
      form.title = data.title;
      form.content = data.content;
    } catch (err) {
      console.error('获取通知数据失败', err);
      return;
    }
  } else {
    editingNotification.value = null;
    form.title = '';
    form.content = '';
  }
  isModalVisible.value = true;
};

// 关闭弹窗
const closeModal = () => {
  isModalVisible.value = false;
};

// 提交通知（新增或编辑）
const submitNotification = async () => {
  try {
    if (editingNotification.value) {
      await request.put(`/manager/notifications/${editingNotification.value.id}`, { ...form });
    } else {
      await request.post('/manager/notifications', { ...form });
    }
    isModalVisible.value = false;
    fetchNotifications();
  } catch (err) {
    console.error('提交通知失败', err);
  }
};

// 删除通知
const deleteNotification = async (id) => {
  try {
    await request.delete(`/manager/notifications/${id}`);
    fetchNotifications();
  } catch (err) {
    console.error('删除通知失败', err);
  }
};

onMounted(fetchNotifications);
</script>