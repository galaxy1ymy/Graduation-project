<template>
  <div class="inner-container">
    <a-card title="个人考勤日报">
        <p style="font-weight: 500; font-size: 18px; margin-bottom: 8px;color: #09b509">
          <check-circle-outlined />&nbsp; {{ todayDate }} 考勤 {{ status }}
        </p>

        <!-- 当日班次 -->
        <a-card title="当日班次" size="small" style="margin-bottom: 8px;">
          默认班次 09:00-18:00
        </a-card>

        <!-- 出勤统计 -->
        <a-card title="出勤统计" size="small">
          打卡 {{ punchCount }} 次，工时 {{ hours }} 小时
          <div style="font-size: 12px; color: #999;">
            数据截至 {{ lastPunchTime }}
          </div>
        </a-card>
    </a-card>

    <a-config-provider :locale="locale">
      <a-calendar v-model:value="value">
        <template #dateCellRender="{ current }">
          <ul class="events">
            <li v-for="item in getListData(current)" :key="item.content">
              <a-badge :status="item.type" :text="item.content" />
            </li>
          </ul>
        </template>

      </a-calendar>
    </a-config-provider>
  </div>

</template>

<script>
import { defineComponent, ref ,onMounted,computed} from 'vue';
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
import axios from 'axios';
import { useStore } from 'vuex';

export default defineComponent({
  setup() {
    const value = ref(dayjs()); // 默认选中今天
    const store = useStore();
    const locale = zhCN;
    const jobNumber = store.getters.getJobNumber;

    const attendanceList = ref([]); // 保存后端返回的打卡数据

    const todayDate = dayjs().format("MM-DD");

    // 1️⃣ 获取打卡数据
    const loadAttendance = async () => {
      try {
        const res = await axios.get(`staff/list?jobNumber=${jobNumber}`);
        attendanceList.value = res.data;
        console.log("考勤数据：", attendanceList.value);
      } catch (error) {
        console.error("获取考勤数据失败：", error);
      }
    };

    // 2️⃣ 获取当天打卡信息
    const getListData = (current) => {
      const currentDate = current.format("YYYY-MM-DD");

      const records = attendanceList.value.filter(
          (item) => dayjs(item.punchTime).format("YYYY-MM-DD") === currentDate
      );

      const isWeekend = current.day() === 0 || current.day() === 6; // 周末
      const isBeforeToday = current.isBefore(dayjs(), "day"); // 是否在今天之前

      // ✅ 只在今天之前判断异常
      if (isBeforeToday && !isWeekend && records.length === 0) {
        return [{ type: "error", content: "异常（未打卡）" }];
      }

      // ✅ 显示正常打卡
      return records.map((item) => ({
        type: item.type === "上班" ? "success" : "processing",
        content: `${item.type}：${dayjs(item.punchTime).format("HH:mm")}`,
      }));
    };

    const todayRecords = computed(() =>
        attendanceList.value.filter(
            item => dayjs(item.punchTime).format("YYYY-MM-DD") === dayjs().format("YYYY-MM-DD")
        )
    );

    const punchCount = computed(() => todayRecords.value.length);

    const hours = computed(() => {
      if (todayRecords.value.length < 2) return 0;
      const first = dayjs(todayRecords.value[0].punchTime);
      const last = dayjs(todayRecords.value[todayRecords.value.length - 1].punchTime);
      return ((last - first) / (1000 * 60 * 60)).toFixed(1);
    });

    const lastPunchTime = computed(() => {
      if (!todayRecords.value.length) return "--";
      return dayjs(todayRecords.value[todayRecords.value.length - 1].punchTime).format("MM-DD HH:mm");
    });

    const status = computed(() => {
      if (!todayRecords.value.length) return "异常（未打卡）";
      return "全部正常";
    });



    onMounted(() => {
      loadAttendance();
    });

    return {
      value,
      locale,
      getListData,
      todayDate,
      lastPunchTime,
      hours,
      status,
      punchCount

    };
  },
});
</script>

<style scoped>
.inner-container {
  height: 100vh;
  overflow-y: auto;
  padding: 16px;
  box-sizing: border-box;
}
/* 隐藏滚动条，但仍然可以滚动 */
.inner-container::-webkit-scrollbar {
  width: 4px; /* 设置滚动条宽度 */
}
.inner-container::-webkit-scrollbar-thumb {
  background-color: rgba(174, 172, 172, 0.2); /* 滑块颜色 */
  border-radius: 3px; /* 圆角 */
}
.inner-container::-webkit-scrollbar-track {
  background-color: transparent; /* 滚动条轨道透明 */
}

.events {
  list-style: none;
  margin: 0;
  padding: 0;
}
.events .ant-badge-status {
  overflow: hidden;
  white-space: nowrap;
  width: 100%;
  text-overflow: ellipsis;
  font-size: 12px;
}

</style>
