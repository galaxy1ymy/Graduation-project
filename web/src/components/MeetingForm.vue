<template>
  <a-form layout="vertical" ref="meetingFormRef" :model="formState">
    <!-- 会议室名称 -->
    <a-form-item label="会议室名称" name="room" :rules="[{ required: true, message: '请填写' }]">
      <a-input v-model:value="formState.room" placeholder="请填写" />
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
    <!-- 会议时长 -->
    <a-form-item label="会议时长(小时)">
      <a-input v-model:value="formState.duration" readonly />
    </a-form-item>

    <!-- 参会人数 -->
    <a-form-item label="参会人数" name="number" :rules="[{ required: true, message: '请填写' }]">
      <a-input-number v-model:value="formState.number" :min="1" style="width: 100%" placeholder="请填写" />
    </a-form-item>


    <!-- 参会人员 -->
    <a-form-item label="参会人员" name="name" :rules="[{ required: true, message: '请填写' }]">
      <a-textarea v-model:value="formState.name" placeholder="请填写" />
    </a-form-item>



  </a-form>
</template>

<script>
import { defineComponent, ref, reactive ,watch} from "vue";
import zhCN from "ant-design-vue/es/locale/zh_CN";
import dayjs from 'dayjs';

export default defineComponent({
  name: "MeetingForm",

  setup(props, { expose }) {
    const meetingFormRef = ref();

    const formState = reactive({
      room: "",
      startTime: null,
      endTime: null,
      number: "",
      name: "",
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
    const validate = () => meetingFormRef.value.validate();

    // 暴露方法给父组件
    expose({
      validate,
      formState
    });

    return {
      meetingFormRef,
      formState,
      zhCN
    };
  }
});
</script>

<style scoped>

</style>
