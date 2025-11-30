<template>
  <a-form layout="vertical" ref="overtimeFormRef" :model="formState">
    <!-- 出差事由 -->
    <a-form-item label="加班事由" name="reason" :rules="[{ required: true, message: '请填写' }]">
      <a-textarea v-model:value="formState.reason" placeholder="请填写" />
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

    <!-- 加班时长 -->
    <a-form-item label="加班时长(小时)">
      <a-input v-model:value="formState.duration" readonly />
    </a-form-item>

  </a-form>
</template>

<script>
import { defineComponent, ref, reactive ,watch} from "vue";
import zhCN from "ant-design-vue/es/locale/zh_CN";
import dayjs from 'dayjs';

export default defineComponent({
  name: "OvertimeForm ",

  setup(props, { expose }) {
    const overtimeFormRef = ref();

    const formState = reactive({
      reason: "",
      startTime: null,
      endTime: null,
      duration: 0,
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
    const validate = () => overtimeFormRef.value.validate();

    // 暴露方法给父组件
    expose({
      validate,
      formState
    });

    return {
      overtimeFormRef,
      formState,
      zhCN
    };
  }
});
</script>

<style scoped>

</style>
