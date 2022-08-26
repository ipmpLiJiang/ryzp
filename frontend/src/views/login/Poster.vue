<template>
  <!-- <div style="width:900px;height:450px;border:1px solid red"> -->
   <div style="width:1013px">
    <a-row type="flex" justify="end">
    <!-- <a style="border:0.5px solid red;padding:3px 12px;" @click="regist">注册</a>
    <div style="float: right;width:10px">&nbsp;</div> -->
    <a style="border:0.5px solid red;padding:3px 12px;margin-bottom:5px;border-radius:3px" @click="returnLogin">登录&nbsp;/&nbsp;注册</a>
    </a-row>
    <a-table
      ref="TableInfo"
      :columns="columns"
      :rowKey="(record) => record.id"
      :dataSource="dataSource"
      :pagination="false"
      :loading="loading"
      :bordered="bordered"
      size="small"
      :scroll="{ x: 980, y: 500 }"
    >
      <template slot="operationTitle" slot-scope="text, record, index">
        <span :title="record.ptit"><a @click="showLook(record)">{{record.ptit}}</a></span>
      </template>
      <template slot="operationPzw" slot-scope="text, record, index">
        <span :title="record.pzw">{{record.pzw}}</span>
      </template>
    </a-table>
    <a-modal :maskClosable="false" :footer="null" v-model="lookVisible" width="85%" title="浏览招聘" @ok="handleLookOk">
      <recruitPoster-look
        ref="zpRecruitPosterLook1"
        @close="handleLookOk"
      >
      </recruitPoster-look>
    </a-modal>
  </div>
</template>

<script>
import moment from 'moment'
import RecruitPosterLook from '../common/RecruitPosterLook.vue'
export default {
  name: 'Poster',
  components: { RecruitPosterLook },
  data () {
    return {
      dataSource: [],
      sortedInfo: null,
      queryParams: {
      },
      loading: false,
      lookVisible: false,
      tableFormat: 'YYYY-MM-DD',
      bordered: true
    }
  },
  computed: {
    columns () {
      return [{
        title: '招聘标题',
        dataIndex: 'ptit',
        scopedSlots: { customRender: 'operationTitle' },
        ellipsis: true,
        fixed: 'left',
        width: 355
      },
      {
        title: '招聘职务',
        dataIndex: 'pzw',
        scopedSlots: { customRender: 'operationPzw' },
        ellipsis: true,
        fixed: 'left',
        width: 350
      },
      {
        title: '发布人',
        dataIndex: 'fbr',
        width: 100
      },
      {
        title: '发布时间',
        dataIndex: 'srtdat',
        customRender: (text, row, index) => {
          if (text !== '' && text !== null) {
            if (isNaN(text) && !isNaN(Date.parse(text))) {
              return moment(text).format(this.tableFormat)
            } else {
              return text
            }
          } else {
            return text
          }
        },
        width: 95
      },
      {
        title: '截止日期',
        dataIndex: 'enddat',
        customRender: (text, row, index) => {
          if (text !== '' && text !== null) {
            if (isNaN(text) && !isNaN(Date.parse(text))) {
              return moment(text).format(this.tableFormat)
            } else {
              return text
            }
          } else {
            return text
          }
        },
        width: 95
      }
      ]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    // regist () {
    //   this.$emit('regist', 'Regist')
    // },
    returnLogin () {
      this.$emit('regist', 'Login')
    },
    handleLookOk () {
      this.lookVisible = false
    },
    showLook (record) {
      this.lookVisible = true
      setTimeout(() => {
        this.$refs.zpRecruitPosterLook1.setFormValues(record)
      }, 200)
    },
    fetch () {
      let params = {
        pageSize: 10,
        pageNum: 1,
        sortField: 'create_Time',
        sortOrder: 'descend'
      }
      this.loading = true
      this.$get('zpRecruitPoster/recruitPosterView', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        this.dataSource = data.rows
      }
      )
    }
  }
}
</script>
<style lang="less" scoped>
</style>
