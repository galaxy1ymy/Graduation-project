<template>
  <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
    <h2>请假审批管理</h2>
    <a-button type="primary" @click="refreshCurrentTab">刷新</a-button>
  </div>

  <a-tabs v-model:activeKey="activeKey">
    <a-tab-pane key="1" tab="请假">
        <a-table
            :columns="getColumns(activeKey)"
            :data-source="getData(activeKey)"
            row-key="id"
            :loading="getLoading(activeKey)"
            :scroll="{ y: 400 }"
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
          :columns="getColumns(activeKey)"
          :data-source="getData(activeKey)"
          row-key="id"
          :loading="getLoading(activeKey)"
          :scroll="{ y: 400 }"
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
          :columns="getColumns(activeKey)"
          :data-source="getData(activeKey)"
          row-key="id"
          :loading="getLoading(activeKey)"
          :scroll="{ y: 400 }"
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
          :columns="getColumns(activeKey)"
          :data-source="getData(activeKey)"
          row-key="id"
          :loading="getLoading(activeKey)"
          :scroll="{ y: 400 }"
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

    <a-tab-pane key="5" tab="会议室预定" force-render>
      <a-table
          :columns="getColumns(activeKey)"
          :data-source="getData(activeKey)"
          row-key="id"
          :loading="getLoading(activeKey)"
          :scroll="{ y: 400 }"
      >
        <template #bodyCell="{ column, record }">

          <!-- 操作列 -->
          <span v-if="column.key === 'action'">
        <a-button
            type="link"
            v-if="record.status === 0"
            @click="approve('meeting', record)"
        >
          通过
        </a-button>

        <a-button
            type="link"
            danger
            v-if="record.status === 0"
            @click="reject('meeting', record)"
        >
          驳回
        </a-button>

        <span v-if="record.status !== 0">
          {{ record.status === 1 ? '已通过' : '已拒绝' }}
        </span>
      </span>

          <!-- 时间格式化 -->
          <span v-else-if="['startTime','endTime','createTime','updateTime','approveTime'].includes(column.key)">
        {{ formatDate(record[column.key]) }}
      </span>

          <span v-else>{{ record[column.key] }}</span>
        </template>
      </a-table>
    </a-tab-pane>

    <a-tab-pane key="6" tab="物品领取" force-render>
      <a-table
          :columns="getColumns(activeKey)"
          :data-source="getData(activeKey)"
          row-key="id"
          :loading="getLoading(activeKey)"
          :scroll="{ y: 400 }"
      >
        <template #bodyCell="{ column, record }">

          <!-- 操作列 -->
          <span v-if="column.key === 'action'">
        <a-button
            type="link"
            v-if="record.status === 0"
            @click="approve('goods', record)"
        >
          通过
        </a-button>

        <a-button
            type="link"
            danger
            v-if="record.status === 0"
            @click="reject('goods', record)"
        >
          驳回
        </a-button>

        <span v-if="record.status !== 0">
          {{ record.status === 1 ? '已通过' : '已拒绝' }}
        </span>
      </span>

          <!-- 时间格式化 -->
          <span v-else-if="['createTime','updateTime','approveTime'].includes(column.key)">
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
    const meetingList = ref([])
    const goodsList = ref([])

    const loadingLeave = ref(false)
    const loadingBusiness = ref(false)
    const loadingOvertime = ref(false)
    const loadingGoingOut = ref(false)
    const loadingMeeting = ref(false)
    const loadingGoods = ref(false)


    const getColumns = (tabKey) => {

      if (tabKey === '1') {
        return [
          { title: 'ID', dataIndex: 'id', key: 'id' },
          { title: '员工号', dataIndex: 'jobNumber', key: 'jobNumber' },
          { title: '姓名', dataIndex: 'employeeName', key: 'employeeName' },
          { title: '类型', dataIndex: 'leaveType', key: 'leaveType' },
          { title: '开始时间', dataIndex: 'startTime', key: 'startTime' },
          { title: '结束时间', dataIndex: 'endTime', key: 'endTime' },
          { title: '时长', dataIndex: 'duration', key: 'duration' },
          { title: '原因', dataIndex: 'reason', key: 'reason' },
          { title: '附件', dataIndex: 'attachment', key: 'attachment' },
          { title: '审批状态', dataIndex: 'status', key: 'status' },
          { title: '操作', key: 'action' }
        ]
      }

      // 会议室
      if (tabKey === '5') {
        return [
          { title: 'ID', dataIndex: 'id', key: 'id' },
          { title: '员工号', dataIndex: 'jobNumber', key: 'jobNumber' },
          { title: '姓名', dataIndex: 'employeeName', key: 'employeeName' },
          { title: '会议室', dataIndex: 'room', key: 'room' },
          { title: '参会人数', dataIndex: 'number', key: 'number' },
          { title: '参会人员', dataIndex: 'name', key: 'name' },
          { title: '开始时间', dataIndex: 'startTime', key: 'startTime' },
          { title: '结束时间', dataIndex: 'endTime', key: 'endTime' },
          { title: '时长', dataIndex: 'meetingDuration', key: 'meetingDuration' },
          { title: '审批状态', dataIndex: 'status', key: 'status' },
          { title: '操作', key: 'action' }
        ]
      }

      // 物品
      if (tabKey === '6') {
        return [
          { title: 'ID', dataIndex: 'id', key: 'id' },
          { title: '员工号', dataIndex: 'jobNumber', key: 'jobNumber' },
          { title: '姓名', dataIndex: 'employeeName', key: 'employeeName' },
          { title: '物品名称', dataIndex: 'name', key: 'name' },
          { title: '数量', dataIndex: 'number', key: 'number' },
          { title: '用途', dataIndex: 'use', key: 'use' },
          { title: '附件', dataIndex: 'attachment', key: 'attachment' },
          { title: '审批状态', dataIndex: 'status', key: 'status' },
          { title: '操作', key: 'action' }
        ]
      }

      // 其他
      return [
        { title: 'ID', dataIndex: 'id', key: 'id' },
        { title: '员工号', dataIndex: 'jobNumber', key: 'jobNumber' },
        { title: '姓名', dataIndex: 'employeeName', key: 'employeeName' },
        { title: '开始时间', dataIndex: 'startTime', key: 'startTime' },
        { title: '结束时间', dataIndex: 'endTime', key: 'endTime' },
        { title: '时长', dataIndex: 'duration', key: 'duration' },
        { title: '原因', dataIndex: 'reason', key: 'reason' },
        { title: '附件', dataIndex: 'attachment', key: 'attachment' },
        { title: '审批状态', dataIndex: 'status', key: 'status' },
        { title: '操作', key: 'action' }
      ]
    }

    const getData = (tabKey) => {
      if (tabKey === '1') return leaveList.value
      if (tabKey === '2') return businessList.value
      if (tabKey === '3') return overtimeList.value
      if (tabKey === '4') return goingOutList.value
      if (tabKey === '5') return meetingList.value
      if (tabKey === '6') return goodsList.value
      return []
    }

    const getLoading = (tabKey) => {
      if (tabKey === '1') return loadingLeave.value
      if (tabKey === '2') return loadingBusiness.value
      if (tabKey === '3') return loadingOvertime.value
      if (tabKey === '4') return loadingGoingOut.value
      if (tabKey === '5') return loadingMeeting.value
      if (tabKey === '6') return loadingGoods.value
      return false
    }


    const formatDate = ts => ts ? new Date(ts).toLocaleString() : ''

    const fetchList = async (type) => {

      const loadingMap = {
        leave: loadingLeave,
        business: loadingBusiness,
        overtime: loadingOvertime,
        goingOut: loadingGoingOut,
        meeting: loadingMeeting,
        goods: loadingGoods
      }

      const listMap = {
        leave: leaveList,
        business: businessList,
        overtime: overtimeList,
        goingOut: goingOutList,
        meeting: meetingList,
        goods: goodsList
      }

      loadingMap[type].value = true

      try {
        const res = await request.get(`/staff/${type}/checkAll`, {
          params: { jobNumber: '1' }
        })

        const list = res || []

        list.sort((a, b) => new Date(b.createTime) - new Date(a.createTime))

        listMap[type].value = list

      } catch (err) {
        console.error(`获取${type}失败`, err)
      } finally {
        loadingMap[type].value = false
      }
    }



    onMounted(() => {
      fetchList('leave')
      fetchList('business')
      fetchList('overtime')
      fetchList('goingOut')
      fetchList('meeting')
      fetchList('goods')
    })

    const refreshCurrentTab = () => {
      if (activeKey.value === '1') fetchList('leave')
      else if (activeKey.value === '2') fetchList('business')
      else if (activeKey.value === '3') fetchList('overtime')
      else if (activeKey.value === '4') fetchList('goingOut')
      else if (activeKey.value === '5') fetchList('meeting')
      else if (activeKey.value === '6') fetchList('goods')
    }

    const approve = async (type, record) => {
      await request.put(`/staff/${type}/updateStatus/${record.id}`, {
        status: 1,
      })
      refreshCurrentTab()
    }

    const reject = async (type, record) => {
      await request.put(`/staff/${type}/updateStatus/${record.id}`, {
        status: 2,
      })
      refreshCurrentTab()
    }


    return {
      activeKey,
      leaveList, businessList, overtimeList, goingOutList,
      loadingLeave, loadingBusiness, loadingOvertime, loadingGoingOut,
      formatDate,getData,getLoading,getColumns,refreshCurrentTab,
      approve,
      reject
    }
  }
})
</script>

<style scoped>
.table-container {
  max-height: 450px;   /* 容器最大高度，可自行调整 */
  overflow-y: auto;    /* 内容超出显示纵向滚动条 */
  margin-bottom: 16px; /* 可选，和其他元素分隔 */
}

</style>