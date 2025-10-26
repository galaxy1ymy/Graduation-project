<template>
  <div style="text-align: center;font-size: 34px;margin-top: 50px">
    <user-outlined />&nbsp; 欢迎您，{{ staffName }}
  </div>

  <div class="content">
    <div class="card">
      <div style="background: #ececec; padding: 20px; border-radius: 8px;">
        <a-card title="考勤打卡" :bordered="false" style="width: 300px">
          <p>上班打卡：{{ punchedUp ? '已打卡('+upTime+')' : '未打卡' }}</p>
          <a-button type="primary" :disabled="punchedUp" @click="punch('上班')">
            上班打卡
          </a-button>

          <p style="margin-top: 10px;">下班打卡：{{ punchedDown ? '已打卡('+downTime+')': '未打卡' }}</p>
          <a-button type="primary" :disabled="punchedDown" @click="punch('下班')">
            下班打卡
          </a-button>
        </a-card>
      </div>
    </div>

    <div class="notice">
      <div style="background: #ececec; padding: 20px; border-radius: 8px;">
        <a-card title="Card title" :bordered="false" style="width: 300px">
          <p>通知</p>
          <p>Card content</p>
          <p>Card content</p>
        </a-card>
      </div>
    </div>

    <div class="calender">
      <div :style="{ width: '300px', border: '1px solid #d9d9d9', borderRadius: '4px' }">
        <a-config-provider :locale="locale">
          <a-calendar
              v-model:value="value"
              :fullscreen="false"
              @panelChange="onPanelChange">
          </a-calendar>
        </a-config-provider>
      </div>
    </div>
  </div>



</template>

<script>
import { defineComponent, computed ,ref,onMounted} from 'vue';
import { useStore } from 'vuex';
import zhCN from 'ant-design-vue/es/locale/zh_CN'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'
import { notification } from 'ant-design-vue';
import axios from 'axios';


dayjs.locale('zh-cn')

export default defineComponent({
  setup() {
    const store = useStore();
    const staffName = computed(() => store.getters.getStaffName);

    const jobNumber = store.getters.getJobNumber;
    const punchedUp = ref(false);
    const punchedDown = ref(false);

    const upTime=ref('');
    const downTime=ref('');


    const value = ref();
    const onPanelChange = (value, mode) => {
      console.log(value, mode);
    };

    const checkStatus = async () => {
      try {
        const res = await axios.get(`staff/list?jobNumber=${jobNumber}`);
        const records = res.data; // 数组

        const today = dayjs().format('YYYY-MM-DD');

        let up = '';
        let down = '';

        records.forEach(record => {
          const dateStr = dayjs(record.punchTime).format('YYYY-MM-DD');
          if (dateStr === today) {
            if (record.type === '上班') up = dayjs(record.createdAt).format('YYYY-MM-DD HH:mm:ss');
            if (record.type === '下班') down = dayjs(record.createdAt).format('YYYY-MM-DD HH:mm:ss');
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
        const res = await axios.post(`staff/punch?jobNumber=${jobNumber}&type=${type}`);
        if(res.data.success){
          notification.success({ description: res.data.message });
          // 打卡成功后刷新当天打卡状态和时间
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
      checkStatus(); // 页面加载时查询打卡状态
    });


    return {
      staffName,
      value,
      onPanelChange,
      locale: zhCN,
      punch,
      punchedUp,
      punchedDown,
      upTime,
      downTime,

    };
  }

});
</script>


<style scoped>
.content {
  display: flex;
  justify-content: flex-start; /* 左对齐 */
  gap: 40px; /* 卡片间间距 */
  padding: 30px;
}

.card, .notice, .calender {
  padding: 0; /* 内部 padding 可在卡片内部控制 */
}


</style>