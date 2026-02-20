<template>
  <div>
    <div style="text-align: center;font-size: 34px;margin-top: 50px">
      <user-outlined />&nbsp; 欢迎您，{{ staffName }}
    </div>

    <div class="content">
      <!-- 考勤打卡卡片 -->
      <div class="card">
        <div style="background: #ececec; padding: 20px; border-radius: 8px;">
          <a-card title="考勤打卡" :bordered="false" style="width: 300px">
            <p>上班打卡：{{ punchedUp ? '已打卡('+upTime+')' : '未打卡' }}</p>
            <a-button type="primary" :disabled="punchedUp" @click="punch('上班')">上班打卡</a-button>

            <p style="margin-top: 10px;">下班打卡：{{ punchedDown ? '已打卡('+downTime+')': '未打卡' }}</p>
            <a-button type="primary" :disabled="punchedDown" @click="punch('下班')">下班打卡</a-button>
          </a-card>
        </div>
      </div>

      <!-- 工作通知卡片 -->
      <div class="notice">
        <div style="background: #ececec; padding: 20px; border-radius: 8px;">
          <a-card title="📢 工作通知" :bordered="false" style="width: 300px">
            <a-list
                bordered
                :data-source="notifications.slice(0, 2)"
                locale="{ emptyText: '暂无通知' }"
            >
              <template #renderItem="{ item }">
                <a-list-item>
                  <a-list-item-meta :title="item.title" :description="item.content" />
                </a-list-item>
              </template>
            </a-list>
          </a-card>
        </div>
      </div>

      <!-- 日历卡片 -->
      <div class="calender">
        <div :style="{ width: '300px', border: '1px solid #d9d9d9', borderRadius: '4px' }">
          <a-config-provider :locale="locale">
            <a-calendar v-model:value="value" :fullscreen="false" @panelChange="onPanelChange"/>
          </a-config-provider>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, computed, ref, onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
import { notification } from 'ant-design-vue';
import axios from 'axios';

dayjs.locale('zh-cn');

export default defineComponent({
  setup() {
    const store = useStore();
    const staffName = computed(() => store.getters.getStaffName);
    const jobNumber = store.getters.getJobNumber;

    // 打卡状态
    const punchedUp = ref(false);
    const punchedDown = ref(false);
    const upTime = ref('');
    const downTime = ref('');

    // 日历
    const value = ref();
    const onPanelChange = (value, mode) => { console.log(value, mode); };

    // 通知列表
    const notifications = ref([]);
    let intervalId = null;

    // 获取通知列表
    const fetchNotifications = async () => {
      try {
        const res = await axios.get('/manager/notifications'); // 前端要能访问后端接口
        notifications.value = res.data;
        notifications.value = res.data.sort((a, b) => b.id - a.id);
      } catch (error) {
        console.error('获取通知列表失败', error);
      }
    };

    // 打卡状态检查
    const checkStatus = async () => {
      try {
        const res = await axios.get('/staff/attendance/list', { params: { jobNumber } });
        const records = res.data;
        const today = dayjs().format('YYYY-MM-DD');

        let up = '';
        let down = '';

        records.forEach(record => {
          const dateStr = dayjs(record.punchTime).format('YYYY-MM-DD');
          if (dateStr === today) {
            if (record.type === '上班') up = dayjs(record.punchTime).format('HH:mm');
            if (record.type === '下班') down = dayjs(record.punchTime).format('HH:mm');
          }
        });

        punchedUp.value = !!up;
        punchedDown.value = !!down;
        upTime.value = up || '';
        downTime.value = down || '';
      } catch (err) {
        console.error(err);
      }
    };

    const punch = async (type) => {
      try {
        const res = await axios.post('/staff/attendance/punch', null, { params: { jobNumber, type } });
        if(res.data.success){
          notification.success({ description: res.data.message });
          await checkStatus();
        } else {
          notification.error({ description: res.data.message });
        }
      } catch(err){
        notification.error({ description: '打卡失败，请重试' });
        console.error(err);
      }
    };

    onMounted(() => {
      checkStatus();
      fetchNotifications();
      intervalId = setInterval(fetchNotifications, 10000); // 自动刷新，每10秒一次
    });

    onUnmounted(() => {
      if(intervalId) clearInterval(intervalId);
    });

    return {
      staffName, value, onPanelChange, locale: zhCN,
      punch, punchedUp, punchedDown, upTime, downTime,
      notifications, fetchNotifications
    };
  }
});
</script>

<style scoped>
.content {
  display: flex;
  justify-content: flex-start;
  gap: 40px;
  padding: 30px;
}
.card, .notice, .calender {
  padding: 0;
}
</style>