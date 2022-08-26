<template>
  <div>
    <a-row>
      <a-col :span="20">
        <b><font style="font-size: 16px;padding-right: 10px;">文章信息</font></b>
        (<font style="color: red">请上传原文至人才办邮箱。仅列一作或通讯文章，5篇以内，注明JCR分区，影响影子*</font>)
      </a-col>
      <a-col :span="4" style="text-align: right">
        <a-button class="editable-add-btn" @click="handleAdd">
          +增加文章信息
        </a-button>
      </a-col>
    </a-row>
    <a-table
      bordered
      :rowKey="record => record.id"
      :data-source="dataSource"
      :columns="columns"
      size="small"
      :scroll="{ x: 900 }"
    >
      <template
        v-for="col in [
          'lwlzmc',
          'zzname',
          'fbqk',
          'fbcbny',
          'slqk',
          'yxyz',
          'tycs',
          'jcrfq',
        ]"
        :slot="col"
        slot-scope="text, record, index"
      >
        <div :key="col">
          <a-input
            style="margin: -5px 0"
            :maxLength="30"
            :value="text"
            @change="(e) => handleChange(e.target.value, record.id, col)"
          />
        </div>
      </template>
      <template
        slot="lwlzmc"
        slot-scope="text, record, index"
      >
        <a-input
          style="margin: -5px 0"
          :maxLength="300"
          :value="text"
          @change="(e) => handleChange(e.target.value, record.id, 'lwlzmc')"
        />
      </template>
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-pdf
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="essay"
          refType="essay"
        >
        </mutiUpload-pdf>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
          v-if="dataSource.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <!-- <a-icon type="delete" theme="twoTone" /> -->
          <a style="color:red" href="javascript:;">delete</a>
        </a-popconfirm>
      </template>
    </a-table>
  </div>
</template>

<script>
import MutiUploadPdf from '../../common/MutiUploadPdf'
export default {
  name: 'StaffEssay',
  components: {
    MutiUploadPdf
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      columns: [{
        title: '文章名称',
        dataIndex: 'lwlzmc',
        scopedSlots: {
          customRender: 'lwlzmc'
        },
        fixed: 'left',
        width: 200
      },
      {
        title: '作者名称',
        dataIndex: 'zzname',
        scopedSlots: {
          customRender: 'zzname'
        },
        fixed: 'left',
        width: 160
      },
      {
        title: '发表期刊',
        dataIndex: 'fbqk',
        scopedSlots: {
          customRender: 'fbqk'
        },
        fixed: 'left',
        width: 160
      },
      {
        title: '发表或出版年度',
        dataIndex: 'fbcbny',
        scopedSlots: {
          customRender: 'fbcbny'
        },
        width: 160
      },
      {
        title: '收录情况',
        dataIndex: 'slqk',
        scopedSlots: {
          customRender: 'slqk'
        },
        width: 160
      },
      {
        title: '影响因子',
        dataIndex: 'yxyz',
        scopedSlots: {
          customRender: 'yxyz'
        },
        width: 160
      },
      {
        title: 'JCR分区',
        dataIndex: 'jcrfq',
        scopedSlots: {
          customRender: 'jcrfq'
        },
        width: 160
      },
      {
        title: '他引次数',
        dataIndex: 'tycs',
        scopedSlots: {
          customRender: 'tycs'
        },
        width: 150
      },
      {
        title: '附件(.pdf)',
        scopedSlots: {
          customRender: 'operationFile'
        },
        fixed: 'right',
        width: 150
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {
          customRender: 'operation'
        },
        fixed: 'right',
        width: 85
      }
      ]
    }
  },
  mounted () {
  },
  methods: {
    setFieldValues (datas, id) {
      this.dataSource = datas
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffEssay', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
        } else {
          // this.$message.error('获取文章信息失败.')
          this.openNotificationIcon('error', '操作提醒', '获取文章信息失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      if (newData.length > 1) {
        const target = newData.filter(item => key === item.id)[0]
        if (target) {
          this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'Y' }).then((r) => {
            if (r.data.data.success === 1) {
              // const index = newData.indexOf(target)
              // newData.splice(index, 1)
              // this.dataSource = newData
              this.openNotificationIcon('success', '操作提醒', '删除文章信息成功.')
              this.dataSource = []
              this.fetch()
            } else {
              // this.$message.error('删除该文章信息失败.')
              this.openNotificationIcon('error', '操作提醒', '删除该文章信息失败.')
            }
          })
        }
      } else {
        // this.$message.error('删除文章信息失败,至少存在一条文章信息数据.')
        this.openNotificationIcon('error', '操作提醒', '删除文章信息失败,至少存在一条文章信息数据.')
      }
    },
    handleChange (value, key, column) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => key === item.id)[0]
      if (target) {
        target[column] = value
        this.dataSource = newData
      }
    },
    handleAdd () {
      if (this.dataSource.length < 5) {
        this.$get('zpStaffInfo/getId', { 'staffId': this.staffId, 'type': 'Y' }).then((r) => {
          if (r.data.data.success === 1) {
            let id = r.data.data.data
            if (id !== null) {
              const {
                dataSource
              } = this
              const newData = {
                id: id,
                lwlzmc: '', // 文章名称
                zzname: '', // 作者名称
                fbqk: '', // 发表期刊
                fbcbny: '', // 发表或出版年度
                slqk: '', // 收录情况
                yxyz: '', // 影响因子
                tycs: '', // 他引次数
                jcrfq: '' // JCR分区
              }
              this.dataSource = [...dataSource, newData]
            } else {
              // this.$message.error('增加文章信息失败.')
              this.openNotificationIcon('error', '操作提醒', '增加文章信息失败.')
            }
          } else {
            // this.$message.error('增加文章信息失败或异常.')
            this.openNotificationIcon('error', '操作提醒', '增加文章信息失败或异常.')
          }
        })
      } else {
        // this.$message.warning('增加文章信息失败，已经有5条文章信息数据了.')
        this.openNotificationIcon('warning', '操作提醒', '增加文章信息失败，已经有5条文章信息数据了.')
      }
    },
    openNotificationIcon (type, title, description) {
      // success, info, warning, error
      this.$notification[type]({
        duration: 3,
        message: title,
        description: description
      })
    }
  }
}
</script>

<style scoped>
.editable-cell {
  position: relative;
}

.editable-cell-input-wrapper,
.editable-cell-text-wrapper {
  padding-right: 24px;
}

.editable-cell-text-wrapper {
  padding: 5px 24px 5px 5px;
}

.editable-cell-icon,
.editable-cell-icon-check {
  position: absolute;
  right: 0;
  width: 20px;
  cursor: pointer;
}

.editable-cell-icon {
  line-height: 18px;
  display: none;
}

.editable-cell-icon-check {
  line-height: 28px;
}

.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}

.editable-cell-icon:hover,
.editable-cell-icon-check:hover {
  color: #108ee9;
}

.editable-add-btn {
  margin-bottom: 8px;
}
</style>
