<template>
  <a-tabs v-model:activeKey="activeKey">
    <a-tab-pane key="1" tab="请假">
      <a-table
          :columns="columns"
          :data-source="leaveList"
          row-key="id"
          :loading="loading"
      >
        <template #bodyCell="{ column, record }">
          <span v-if="column.key === 'action'">
  <a-button type="link" v-if="record.status === 0" @click="approve('leave', record)">通过</a-button>
  <a-button type="link" danger v-if="record.status === 0" @click="reject('leave', record)">驳回</a-button>
  <span v-if="record.status !== 0">
    {{ record.status === 1 ? '已通过' : '已拒绝' }}
  </span>
</span>

          <span v-else-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
            {{ formatDate(record[column.key]) }}
          </span>

          <span v-else>{{ record[column.key] }}</span>
        </template>
      </a-table>
    </a-tab-pane>

    <!-- 出差 -->
    <a-tab-pane key="2" tab="出差" force-render>
      <a-table
          :columns="columns"
          :data-source="businessList"
          row-key="id"
          :loading="loadingBusiness"
      >
        <template #bodyCell="{ column, record }">
          <span v-if="column.key === 'action'">
            <a-button type="link" v-if="record.status === 0" @click="approve('business', record)">通过</a-button>
            <a-button type="link" danger v-if="record.status === 0" @click="reject('business', record)">驳回</a-button>
            <span v-if="record.status !== 0">{{ record.status === 1 ? '已通过' : '已拒绝' }}</span>
          </span>
          <span v-else-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
            {{ formatDate(record[column.key]) }}
          </span>
          <span v-else>{{ record[column.key] }}</span>
        </template>
      </a-table>
    </a-tab-pane>

    <!-- 加班 -->
    <a-tab-pane key="3" tab="加班" force-render>
      <a-table
          :columns="columns"
          :data-source="overtimeList"
          row-key="id"
          :loading="loadingOvertime"
      >
        <template #bodyCell="{ column, record }">
          <span v-if="column.key === 'action'">
            <a-button type="link" v-if="record.status === 0" @click="approve('overtime', record)">通过</a-button>
            <a-button type="link" danger v-if="record.status === 0" @click="reject('overtime', record)">驳回</a-button>
            <span v-if="record.status !== 0">{{ record.status === 1 ? '已通过' : '已拒绝' }}</span>
          </span>
          <span v-else-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
            {{ formatDate(record[column.key]) }}
          </span>
          <span v-else>{{ record[column.key] }}</span>
        </template>
      </a-table>
    </a-tab-pane>

    <!-- 外出 -->
    <a-tab-pane key="4" tab="外出" force-render>
      <a-table
          :columns="columns"
          :data-source="goingOutList"
          row-key="id"
          :loading="loadingGoingOut"
      >
        <template #bodyCell="{ column, record }">
          <span v-if="column.key === 'action'">
            <a-button type="link" v-if="record.status === 0" @click="approve('goingOut', record)">通过</a-button>
            <a-button type="link" danger v-if="record.status === 0" @click="reject('goingOut', record)">驳回</a-button>
            <span v-if="record.status !== 0">{{ record.status === 1 ? '已通过' : '已拒绝' }}</span>
          </span>
          <span v-else-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
            {{ formatDate(record[column.key]) }}
          </span>
          <span v-else>{{ record[column.key] }}</span>
        </template>
      </a-table>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import request from '@/utils/request'

export default defineComponent({
  setup() {
    const activeKey = ref('1')
    const leaveList = ref([])
    const businessList = ref([])
    const overtimeList = ref([])
    const goingOutList = ref([])

    const loadingLeave = ref(false)
    const loadingBusiness = ref(false)
    const loadingOvertime = ref(false)
    const loadingGoingOut = ref(false)


    const columns = [
      {title: 'ID', dataIndex: 'id', key: 'id'},
      {title: '员工号', dataIndex: 'jobNumber', key: 'jobNumber'},
      {title: '姓名', dataIndex: 'name', key: 'name'},
      {title: '类型', dataIndex: 'leaveType', key: 'leaveType'},
      {title: '开始时间', dataIndex: 'startTime', key: 'startTime'},
      {title: '结束时间', dataIndex: 'endTime', key: 'endTime'},
      {title: '请假时长', dataIndex: 'leaveDuration', key: 'leaveDuration'},
      {title: '请假原因', dataIndex: 'reason', key: 'reason'},
      {title: '附件', dataIndex: 'attachment', key: 'attachment'},
      {title: '审批状态', dataIndex: 'status', key: 'status'},
      {title: '操作', key: 'action'},
    ]

    const formatDate = ts => ts ? new Date(ts).toLocaleString() : ''

    const fetchList = async (type) => {
      const loadingMap = {
        leave: loadingLeave,
        business: loadingBusiness,
        overtime: loadingOvertime,
        goingOut: loadingGoingOut
      }
      const listMap = {
        leave: leaveList,
        business: businessList,
        overtime: overtimeList,
        goingOut: goingOutList
      }

      loadingMap[type].value = true
      try {
        const res = await request.get(`/staff/${type}/checkAll`, {params: {jobNumber: '1'}})
        listMap[type].value = res
      } catch (err) {
        console.error(`获取${type}记录失败`, err)
      } finally {
        loadingMap[type].value = false
      }
    }

    const approve = async (type, record) => {
      try {
        await request.put(`/staff/${type}/updateStatus/${record.id}`, {status: 1})
        record.status = 1
      } catch (err) {
        console.error('审批失败', err)
      }
    }

    const reject = async (type, record) => {
      try {
        await request.put(`/staff/${type}/updateStatus/${record.id}`, {status: 2})
        record.status = 2
      } catch (err) {
        console.error('驳回失败', err)
      }
    }

    onMounted(() => {
      fetchList('leave')
      fetchList('business')
      fetchList('overtime')
      fetchList('goingOut')
    })

    return {
      activeKey,
      leaveList, businessList, overtimeList, goingOutList,
      loadingLeave, loadingBusiness, loadingOvertime, loadingGoingOut,
      columns, approve, reject, formatDate
    }
  }
})
</script>

<style scoped>
/* 可根据需要调整表格和按钮样式 */
</style>