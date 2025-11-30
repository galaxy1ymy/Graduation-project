<template>
  <a-form layout="vertical" ref="leaveFormRef" :model="formState">
    <!-- 请假类型 -->
    <a-form-item label="请假类型" name="leaveType" :rules="[{ required: true, message: '请选择请假类型' }]">
      <a-select v-model:value="formState.leaveType" placeholder="请选择">
        <a-select-option value="年假">年假</a-select-option>
        <a-select-option value="事假">事假</a-select-option>
        <a-select-option value="病假">病假</a-select-option>
        <a-select-option value="调休假">调休假</a-select-option>
        <a-select-option value="婚假">婚假</a-select-option>
        <a-select-option value="产假">产假</a-select-option>
        <a-select-option value="其他">其他</a-select-option>
      </a-select>
    </a-form-item>

    <!-- 开始时间 -->
    <a-form-item label="开始时间" name="startTime" :rules="[{ required: true, message: '请选择开始时间' }]">
      <a-config-provider :locale="zhCN">
        <a-date-picker
            v-model:value="formState.startTime"
            show-time
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm"
        />
      </a-config-provider>
    </a-form-item>

    <!-- 结束时间 -->
    <a-form-item label="结束时间" name="endTime" :rules="[{ required: true, message: '请选择结束时间' }]">
      <a-config-provider :locale="zhCN">
        <a-date-picker
            v-model:value="formState.endTime"
            show-time
            format="YYYY-MM-DD HH:mm"
            value-format="YYYY-MM-DD HH:mm"
        />
      </a-config-provider>
    </a-form-item>

    <!-- 请假时长 -->
    <a-form-item label="请假时长(小时)">
      <a-input v-model:value="formState.duration" readonly />
    </a-form-item>

    <!-- 请假事由 -->
    <a-form-item label="请假事由" name="reason" :rules="[{ required: true, message: '请输入请假原因' }]">
      <a-textarea v-model:value="formState.reason" placeholder="请输入请假事由" />
    </a-form-item>

    <!-- 上传附件 -->
    <a-form-item label="说明附件">
      <a-upload-dragger
          v-model:fileList="formState.files"
          name="files"
          action="/upload.do"
      >
        <plus-circle-outlined /> 上传附件
      </a-upload-dragger>
    </a-form-item>
  </a-form>
</template>

<script>
import { defineComponent, ref, reactive ,watch} from "vue";
import zhCN from "ant-design-vue/es/locale/zh_CN";
import dayjs from 'dayjs';

export default defineComponent({
  name: "LeaveForm",

  setup(props, { expose }) {
    const leaveFormRef = ref();

    const formState = reactive({
      leaveType: "",
      startTime: null,
      endTime: null,
      duration: 0,
      reason: "",
      files: []
    });

    watch(
        () => [formState.startTime, formState.endTime],
        ([start, end]) => {
          if (start && end) {
            const diff = dayjs(end).diff(dayjs(start), "hour", true);
            formState.duration = diff > 0 ? diff.toFixed(2) : 0;
          } else {
            formState.duration = 0;
          }
        }
    );



    // 父组件调用的 validate()
    const validate = () => leaveFormRef.value.validate();

    // 暴露方法给父组件
    expose({
      validate,
      formState
    });

    return {
      leaveFormRef,
      formState,
      zhCN
    };
  }
});
</script>

<style scoped>
</style>
