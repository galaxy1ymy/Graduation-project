<template>
  <a-card title="假勤">
    <a-card-grid
        v-for="(item, index) in menuList1"
        :key="index"
        class="grid-item"
    >
      <a-button class="full-btn" @click="showModal(item)">
        <component :is="item.icon" />
        <div>{{ item.label }}</div>
      </a-button>
    </a-card-grid>
  </a-card>
  <br>
  <a-card title="行政">
    <a-card-grid
        v-for="(item, index) in menuList2"
        :key="index"
        class="grid-item"
    >
      <a-button class="full-btn" @click="showModal(item)">
        <component :is="item.icon" />
        <div>{{ item.label }}</div>
      </a-button>
    </a-card-grid>
  </a-card>

  <a-modal
      v-model:visible="visible"
      :title="modalTitle"
      @cancel="handleCancel"
      :footer="null"
  >
    <LeaveForm v-if="currentType === 'leave'" ref="leaveRef" />
    <TravelForm v-if="currentType === 'travel'" ref="travelRef" />
    <OvertimeForm v-if="currentType === 'overtime'" ref="overtimeRef" />
    <OutForm v-if="currentType === 'out'" ref="outRef" />

    <MeetingForm v-if="currentType === 'meeting'" ref="meetingRef" />
    <GoodsForm v-if="currentType === 'goods'" ref="goodsRef" />

    <div style="text-align: right; margin-top: 20px">
      <a-button @click="handleCancel">取消</a-button>
      <a-button
          type="primary"
          class="ml-10"
          :loading="loading"
          @click="submitOk"
      >
        提交
      </a-button>
    </div>
  </a-modal>
</template>
<script>
import {
  ScheduleOutlined,
  CarOutlined,
  ClockCircleOutlined,
  ShoppingOutlined,
  DesktopOutlined,
  ToolOutlined
} from "@ant-design/icons-vue";
import { ref,defineComponent } from "vue";
import { message } from "ant-design-vue";
import LeaveForm from "@/components/LeaveForm.vue";
import TravelForm from "@/components/TravelForm.vue";
import OvertimeForm from "@/components/OvertimeForm.vue";
import OutForm from "@/components/OutForm.vue";
import MeetingForm from "@/components/MeetingForm.vue";
import GoodsForm from "@/components/GoodsForm.vue";
import { useStore } from "vuex";
import axios from "axios";

export default defineComponent({
  components:{
    LeaveForm,
    TravelForm,
    OvertimeForm,
    OutForm,
    MeetingForm,
    GoodsForm
  },
  setup() {

// 弹窗
    const store = useStore();
    const visible = ref(false);
    const currentType = ref("");
    const modalTitle = ref("");
    const loading = ref(false);


// 子表单 ref
    const leaveRef = ref();
    const overtimeRef = ref();
    const travelRef = ref();
    const outRef = ref();
    const meetingRef = ref();
    const goodsRef = ref();

    const menuList1 = [
      {label: "请假", icon: ScheduleOutlined, type: "leave"},
      {label: "出差", icon: CarOutlined, type: "travel"},
      {label: "加班", icon: ClockCircleOutlined, type: "overtime"},
      {label: "外出", icon: ShoppingOutlined, type: "out"}
    ];
    const menuList2 = [
      {label: '会议室预定', icon: DesktopOutlined, type: 'meeting'},
      {label: '物品领取', icon: ToolOutlined, type: 'goods'},
    ]

// 打开弹窗
    const showModal = (item) => {
      visible.value = true;
      modalTitle.value = item.label;
      currentType.value = item.type;
    };

// 关闭
    const handleCancel = () => {
      visible.value = false;
    };

    /** 获取附件 */
    const getAttachment = (form) => {
      if (form.files && form.files.length > 0) {
        return form.files[0].response?.url || form.files[0].name || "";
      }
      return "";
    };

    /** 提交配置（核心） */
    const submitConfig = {
      leave: {
        ref: leaveRef,
        url: "staff/leave/create",
        successMsg: "请假申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            leaveType: form.leaveType,
            startTime: form.startTime,
            endTime: form.endTime,
            leaveDuration: Number(form.duration),
            reason: form.reason,
            attachment: getAttachment(form)
          };
        },
        reset(form) {
          Object.assign(form, {
            leaveType: "",
            startTime: null,
            endTime: null,
            duration: 0,
            reason: "",
            files: []
          });
        }
      },

      travel: {
        ref: travelRef,
        url: "staff/business/create",
        successMsg: "出差申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            startTime: form.startTime,
            endTime: form.endTime,
            businessDuration: Number(form.duration),
            reason: form.reason,
            attachment: getAttachment(form)
          };
        },
        reset(form) {
          Object.assign(form, {
            startTime: null,
            endTime: null,
            duration: 0,
            reason: "",
            files: []
          });
        }
      },

      overtime: {
        ref: overtimeRef,
        url: "staff/overtime/create",
        successMsg: "加班申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            startTime: form.startTime,
            endTime: form.endTime,
            overtimeDuration: Number(form.duration),
            reason: form.reason
          };
        },
        reset(form) {
          Object.assign(form, {
            startTime: null,
            endTime: null,
            duration: 0,
            reason: ""
          });
        }
      },

      out: {
        ref: outRef,
        url: "staff/goingOut/create",
        successMsg: "外出申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            startTime: form.startTime,
            endTime: form.endTime,
            goingoutDuration: Number(form.duration),
            reason: form.reason,
            address: form.address
          };
        },
        reset(form) {
          Object.assign(form, {
            startTime: null,
            endTime: null,
            duration: 0,
            reason: "",
            address: ""
          });
        }
      },

      meeting: {
        ref: meetingRef,
        url: "staff/meeting/create",
        successMsg: "会议室预定申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            startTime: form.startTime,
            endTime: form.endTime,
            meetingDuration: Number(form.duration),
            room: form.room,
            number: form.number,
            name: form.name

          };
        },
        reset(form) {
          Object.assign(form, {
            startTime: null,
            endTime: null,
            duration: 0,
            room: "",
            number: "",
            name: ""
          });
        }
      },

      goods: {
        ref: goodsRef,
        url: "staff/goods/create",
        successMsg: "物品领取申请提交成功",
        buildPayload(form) {
          return {
            jobNumber: store.state.jobNumber,
            staffName: store.state.staffName,
            use: form.use,
            name: form.name,
            number: form.number,
            attachment: getAttachment(form)
          };
        },
        reset(form) {
          Object.assign(form, {
            use: "",
            name: "",
            number: "",
            files: []
          });
        }
      },


    };

    /** 统一提交 */
    const submitOk = async () => {
      const config = submitConfig[currentType.value];
      if (!config) return;

      try {
        loading.value = true;

        await config.ref.value.validate();
        const form = config.ref.value.formState;

        const payload = config.buildPayload(form);
        const res = await axios.post(config.url, payload);

        if (res.data === "success") {
          message.success(config.successMsg);
          config.reset(form);
          visible.value = false;
        } else {
          message.error("提交失败");
        }
      } catch (e) {
        message.warning("请完整填写表单");
      } finally {
        loading.value = false;
      }
    };

    return {
      visible,
      currentType,
      modalTitle,
      loading,
      menuList1,
      menuList2,
      leaveRef,
      travelRef,
      overtimeRef,
      outRef,
      meetingRef,
      goodsRef,
      showModal,
      handleCancel,
      submitOk
    };
  }
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