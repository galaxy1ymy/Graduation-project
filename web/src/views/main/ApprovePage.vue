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
<!--    <OvertimeForm v-if="currentType === 'overtime'" ref="overtimeRef" />-->
<!--    <TravelForm v-if="currentType === 'travel'" ref="travelRef" />-->
<!--    <OutForm v-if="currentType === 'out'" ref="outRef" />-->

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
import { useStore } from "vuex";
import axios from "axios";

export default defineComponent({
  components:{
    LeaveForm
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

    const menuList1 = [
      { label: "请假", icon: ScheduleOutlined, type: "leave" },
      { label: "出差", icon: CarOutlined, type: "travel" },
      { label: "加班", icon: ClockCircleOutlined, type: "overtime" },
      { label: "外出", icon: ShoppingOutlined, type: "out" }
    ];
    const menuList2 = [
      { label: '会议室预定', icon: DesktopOutlined, type: 'meeting'},
      { label: '物品领取', icon: ToolOutlined, type: 'goods'},
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

// 提交
    const submitOk = async () => {
      try {
        loading.value = true;

        if (currentType.value === "leave") {
          // 1. 校验必填字段（附件不参与校验）
          await leaveRef.value.validate();
          const form = leaveRef.value.formState;

          // 2. 获取当前登录用户
          const jobNumber = store.state.jobNumber;
          const staffName = store.state.staffName;

          // 3. 处理附件，可选
          let attachment = "";
          if (form.files && form.files.length > 0) {
            attachment = form.files[0].response?.url || form.files[0].name || "";
          }

          // 4. 构建提交数据
          const payload = {
            jobNumber,
            staffName,
            leaveType: form.leaveType,
            startTime: form.startTime,
            endTime: form.endTime,
            leaveDuration: Number(form.duration),  // 转成数字
            reason: form.reason,
            attachment
          };

          // 5. 提交到后端
          const res = await axios.post("staff/leave/create", payload);
          if (res.data === "success") {
            message.success("请假申请提交成功");
            // 清空表单
            Object.assign(form, {
              leaveType: "",
              startTime: null,
              endTime: null,
              duration: 0,
              reason: "",
              files: []
            });
            visible.value = false;
          } else {
            message.error("提交失败");
          }
        }

        loading.value = false;

      } catch (e) {
        loading.value = false;
        message.warning("请完整填写表单");
      }
    };



    return {
      visible,
      currentType,
      modalTitle,
      loading,
      leaveRef,
      overtimeRef,
      travelRef,
      outRef,
      menuList1,
      menuList2,
      showModal,
      handleCancel,
      submitOk,
    }
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