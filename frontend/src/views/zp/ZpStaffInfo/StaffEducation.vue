<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b
          ><font style="font-size: 16px; padding-right: 10px"
            >教育经历Education experience</font
          ></b
        >
        (<font style="color: red">从本科起，按时间正序填写*</font>)
      </a-col>
      <a-col :span="3" style="text-align: right">
        <a-button class="editable-add-btn" @click="handleAdd">
          +增加教育经历
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
        v-for="col in ['xlxw', 'yxname', 'sxzy', 'yjfx', 'dsxx']"
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
        v-for="col1 in ['srtdat', 'enddat']"
        :slot="col1"
        slot-scope="text, record, index"
      >
        <div :key="col1">
          <a-date-picker
            :value="text"
            @change="(e) => handleChange(e, record.id, col1)"
          />
        </div>
      </template>
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-file
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="education"
          refType="education"
        >
        </mutiUpload-file>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a-popconfirm
          v-if="dataSource.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <!-- <a-icon type="delete" theme="twoTone" /> -->
          <a style="color: red" href="javascript:;">delete</a>
        </a-popconfirm>
      </template>
    </a-table>
  </div>
</template>

<script>
import MutiUploadFile from '../../common/MutiUploadFile'
export default {
  name: 'StaffEducation',
  components: {
    MutiUploadFile
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      columns: [{
        title: '学历/学位',
        dataIndex: 'xlxw',
        scopedSlots: {
          customRender: 'xlxw'
        },
        fixed: 'left',
        width: 160
      },
      {
        title: '起始时间',
        dataIndex: 'srtdat',
        scopedSlots: {
          customRender: 'srtdat'
        },
        fixed: 'left',
        width: 150
      },
      {
        title: '终止时间',
        dataIndex: 'enddat',
        scopedSlots: {
          customRender: 'enddat'
        },
        fixed: 'left',
        width: 150
      },
      {
        title: '院校',
        dataIndex: 'yxname',
        scopedSlots: {
          customRender: 'yxname'
        },
        width: 200
      },
      {
        title: '所学专业',
        dataIndex: 'sxzy',
        scopedSlots: {
          customRender: 'sxzy'
        },
        width: 200
      },
      {
        title: '研究方向',
        dataIndex: 'yjfx',
        scopedSlots: {
          customRender: 'yjfx'
        },
        width: 200
      },
      {
        title: '导师信息',
        dataIndex: 'dsxx',
        scopedSlots: {
          customRender: 'dsxx'
        },
        width: 180
      },
      {
        title: '附件',
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
      this.$get('zpStaffInfo/findStaffEducation', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
        } else {
          // this.$message.error('获取教育经历失败.')
          this.openNotificationIcon('error', '操作提醒', '获取教育经历失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      if (newData.length > 1) {
        const target = newData.filter(item => key === item.id)[0]
        if (target) {
          this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'E' }).then((r) => {
            if (r.data.data.success === 1) {
              // const index = newData.indexOf(target)
              // newData.splice(index, 1)
              // this.dataSource = newData
              this.openNotificationIcon('success', '操作提醒', '删除教育经历成功.')
              this.dataSource = []
              this.fetch()
            } else {
              // this.$message.error('删除教育经历成功.')
              this.openNotificationIcon('error', '操作提醒', '删除教育经历失败.')
            }
          })
        }
      } else {
        // this.$message.error('删除教育经历失败,至少存在一条教育经历数据.')
        this.openNotificationIcon('error', '操作提醒', '删除教育经历失败,至少存在一条教育经历数据.')
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
      this.$get('zpStaffInfo/getId', { 'staffId': this.staffId, 'type': 'E' }).then((r) => {
        if (r.data.data.success === 1) {
          let id = r.data.data.data
          if (id !== null) {
            const {
              dataSource
            } = this
            const newData = {
              id: id,
              xlxw: '', // 学历/学位
              srtdat: '', // 起始时间
              enddat: '', // 终止时间
              yxname: '', // 院校
              sxzy: '', // 所学专业
              yjfx: '', // 研究方向
              dsxx: '' // 导师信息
            }
            this.dataSource = [...dataSource, newData]
          } else {
            // this.$message.error('增加教育经历失败.')
            this.openNotificationIcon('error', '操作提醒', '增加教育经历失败.')
          }
        } else {
          // this.$message.error('增加教育经历失败或异常.')
          this.openNotificationIcon('error', '操作提醒', '增加教育经历失败或异常.')
        }
      })
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
