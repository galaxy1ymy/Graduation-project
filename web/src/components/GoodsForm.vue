<template>
  <a-form layout="vertical" ref="goodsFormRef" :model="formState">
    <!-- 物品领用 -->
    <a-form-item label="用途" name="use" :rules="[{ required: true, message: '请填写' }]">
      <a-textarea v-model:value="formState.use" placeholder="请填写" />
    </a-form-item>

    <div>明细</div>


    <!-- 加班时长 -->
    <!-- 物品领用 -->
    <a-form-item label="物品名称" name="name" :rules="[{ required: true, message: '请填写' }]">
      <a-input v-model:value="formState.name" placeholder="请填写" />
    </a-form-item>
    <a-form-item label="物品数量" name="number" :rules="[{ required: true, message: '请填写' }]">
      <a-input v-model:value="formState.number" placeholder="请填写" />
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
import { defineComponent, ref, reactive} from "vue";

export default defineComponent({
  name: "GoodsForm",

  setup(props, { expose }) {
    const goodsFormRef = ref();

    const formState = reactive({
      use: "",
      name: "",
      number: "",
      files: []
    });


    // 父组件调用的 validate()
    const validate = () => goodsFormRef.value.validate();

    // 暴露方法给父组件
    expose({
      validate,
      formState
    });

    return {
      goodsFormRef,
      formState,
    };
  }
});
</script>

<style scoped>

</style>
