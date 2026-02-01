<template>
  <div style="background-color: #ececec; padding: 20px">
    <a-row :gutter="16">
      <a-col :span="8">
        <a-card title="系统用户" :bordered="false">
          <p class="num">{{ totalStaff }}</p>
        </a-card>
      </a-col>

      <a-col :span="8">
        <a-card title="在职员工" :bordered="false">
          <p class="num">{{ activeStaff }}</p>
        </a-card>
      </a-col>

      <a-col :span="8">
        <a-card title="今日出勤" :bordered="false">
          <p class="num">{{ todayAttendance }}</p>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request'

const totalStaff = ref(0)
const activeStaff = ref(0)
const todayAttendance = ref(0)

const loadData = async () => {
  // 1️⃣ 系统用户总数
  const staffRes = await request.get('/staff/count')
  totalStaff.value = staffRes

  // 2️⃣ 在职员工（示例接口）
  const activeRes = await request.get('/staff/active/count')
  activeStaff.value = activeRes

  // 3️⃣ 今日出勤
  const attendanceRes = await request.get('/staff/attendance/todayCount')
  todayAttendance.value = attendanceRes
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.num {
  font-size: 36px;
  font-weight: bold;
  text-align: center;
  color:#1677ff;
}
</style>
