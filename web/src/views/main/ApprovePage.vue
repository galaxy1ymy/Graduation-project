<template>
  <a-tabs v-model:activeKey="activeKey">

    <!-- ================= 发起申请 ================= -->
    <a-tab-pane key="1" tab="发起申请">

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

    </a-tab-pane>


    <!-- ================= 已提交 ================= -->
    <!-- 已提交 Tab -->
    <a-tab-pane key="2" tab="已提交">
      <div style="display: flex; justify-content: flex-end; margin-bottom: 8px;">
        <a-button type="primary" @click="fetchAll">刷新</a-button>
      </div>

        <a-table
            :columns="columns"
            :data-source="pagedList"
            :pagination="pagination"
            :loading="loading"
            @change="handleTableChange"
            :scroll="{ y: '60vh' }"
        >
          <template #bodyCell="{ column, record }">
        <span v-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
          {{ formatDate(record[column.key]) }}
        </span>
            <span v-else-if="column.key === 'status'">
          <a-tag v-if="record.status === 0" color="orange">待审批</a-tag>
          <a-tag v-else-if="record.status === 1" color="green">已通过</a-tag>
          <a-tag v-else color="red">已拒绝</a-tag>
        </span>
            <span v-else>{{ record[column.key] }}</span>
          </template>
        </a-table>
    </a-tab-pane>



  </a-tabs>


  <!-- ================= 弹窗保持不变 ================= -->
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
import { ref,defineComponent,onMounted,computed } from "vue";
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
    const activeKey = ref('1');

    const store = useStore();
    const visible = ref(false);
    const currentType = ref("");
    const modalTitle = ref("");
    const loading = ref(false);

    const allList = ref([])
    const formatDate = ts => ts ? new Date(ts).toLocaleString() : ''



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
        url: "/staff/leave/create",
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
        url: "/staff/business/create",
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
        url: "/staff/overtime/create",
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
        url: "/staff/goingOut/create",
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
        url: "/staff/meeting/create",
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
        url: "/staff/goods/create",
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


    const columns = [
      { title: '申请类型', dataIndex: 'type', key: 'type' },  // ⭐ 新增
      { title: '开始时间', dataIndex: 'startTime', key: 'startTime' },
      { title: '结束时间', dataIndex: 'endTime', key: 'endTime' },
      { title: '时长', dataIndex: 'duration', key: 'duration' },
      { title: '原因', dataIndex: 'reason', key: 'reason' },
      { title: '附件', dataIndex: 'attachment', key: 'attachment' },
      { title: '审批状态', dataIndex: 'status', key: 'status' }
    ]

    // 获取数据后同步 total
    const fetchAll = async () => {
      loading.value = true
      try {
        const jobNumber = '1'
        const [leaveRes, businessRes, overtimeRes, goingOutRes] =
            await Promise.all([
              axios.get('/staff/leave/checkAll', { params: { jobNumber } }),
              axios.get('/staff/business/checkAll', { params: { jobNumber } }),
              axios.get('/staff/overtime/checkAll', { params: { jobNumber } }),
              axios.get('/staff/goingOut/checkAll', { params: { jobNumber } }),
            ])

        const formatList = (list, typeName, durationKey) =>
            Array.isArray(list)
                ? list.map(item => ({
                  ...item,
                  type: typeName,
                  duration: item[durationKey]
                }))
                : []

        allList.value = [
          ...formatList(leaveRes.data, '请假', 'leaveDuration'),
          ...formatList(businessRes.data, '出差', 'businessDuration'),
          ...formatList(overtimeRes.data, '加班', 'overtimeDuration'),
          ...formatList(goingOutRes.data, '外出', 'goingoutDuration')
        ]

        allList.value.sort((a, b) => new Date(b.createTime) - new Date(a.createTime))

        pagination.value.total = allList.value.length  // ✅ 同步 total

      } catch (err) {
        console.error('获取数据失败', err)
      } finally {
        loading.value = false
      }
    }



    onMounted(() => {
      fetchAll()
    })

    // 分页对象
    const pagination = ref({
      current: 1,
      pageSize: 10,
      total: 0,
      showSizeChanger: true,
      pageSizeOptions: ['10', '20', '50', '100', '全部'],  // 支持“全部”
      showTotal: total => `共 ${total} 条`
    })

// 计算当前页显示数据
    const pagedList = computed(() => {
      if (pagination.value.pageSize === allList.value.length) {
        return allList.value  // 显示全部
      }
      const start = (pagination.value.current - 1) * pagination.value.pageSize
      const end = start + pagination.value.pageSize
      return allList.value.slice(start, end)
    })

// 分页变化处理
    const handleTableChange = (pager) => {
      pagination.value.current = pager.current
      if (pager.pageSize === '全部') {
        pagination.value.pageSize = allList.value.length
      } else {
        pagination.value.pageSize = Number(pager.pageSize)
      }
    }





    return {
      visible,
      activeKey,
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
      submitOk,
      allList,
      columns,
      formatDate,
      pagedList,
      handleTableChange,
      pagination,
      fetchAll
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