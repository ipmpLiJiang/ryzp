<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b
          ><font style="font-size: 16px; padding-right: 10px"
            >教育经历</font
          ></b
        >
        (<font style="color: red">从高中起，按时间正序填写*</font>)
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
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-look
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="education"
          refType="education"
        >
        </mutiUpload-look>
      </template>
      <template slot="operation" slot-scope="text, record">
        <a @click="handleEdit(record)" href="javascript:;">编辑</a>
        <a-divider type="vertical" />
        <a-popconfirm
          v-if="dataSource.length"
          title="Sure to delete?"
          @confirm="() => onDelete(record.id)"
        >
          <a style="color: red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      class="ant-modal-nofooter"
      :footer="null"
      v-model="coreVisible"
      :title="btnType === 'addBtn' ? '新增教育经历' : '修改教育经历'"
      :width="700"
      :maskClosable="false"
      @ok="handleOk"
      @cancel="handleOk"
    >
      <a-form-model
        ref="formData"
        :model="formData"
        :rules="formDataRules"
        v-bind="layout"
      >
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="院校名称" prop="yxname">
            <a-input
              placeholder="请输入院校名称"
              v-model="formData.yxname"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="起始时间" prop="srtdat" v-bind="layout2">
            <a-date-picker
              v-model="formData.srtdat"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="终止时间" prop="enddat" v-bind="layout3">
            <a-date-picker
              v-model="formData.enddat"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="学历" prop="xlxw" v-bind="layout2">
            <a-select
              v-model="formData.xlxw"
              @change="xlxwChange"
              placeholder="请选择学历"
            >
              <a-select-option v-for="xx in xlList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
          </a-col>
        <a-col :span="12">
          <a-form-model-item label="导师" prop="dsxx" v-bind="layout3">
            <a-input
              placeholder="请输入导师"
              v-model="formData.dsxx"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="学科类型" prop="xklx" v-bind="layout2">
            <a-select
              v-model="formData.xklx"
              :disabled="!xklxReq"
              placeholder="请选择学科类型"
            >
              <a-select-option v-for="xx in xklxList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="学科专业1" prop="xkzy1">
            <a-input
              placeholder="请输入学科专业1"
              v-model="formData.xkzy1"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="学科专业2" prop="xkzy2">
            <a-input
              placeholder="请输入学科专业2"
              v-model="formData.xkzy2"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="研究方向" prop="yjfx">
            <a-input
              placeholder="请输入研究方向"
              v-model="formData.yjfx"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <mutiUpload-pdf
            :baseId="formData.id"
            refTab="education"
            refType="education"
          >
          </mutiUpload-pdf>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          &nbsp;
        </a-col>
        <a-col :span="12">
        <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button class="editable-add-btn" @click="resetForm()">
            重置
          </a-button>
          <a-button type="primary" :loading="loading" style="margin-left: 20px;" @click="editSubmit">
            确认
          </a-button>
        </a-form-model-item>
        </a-col>
      </a-row>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import MutiUploadPdf from '../../common/MutiUploadPdf'
import MutiUploadLook from '../../common/MutiUploadLook'
import moment from 'moment'
export default {
  name: 'StaffEducation',
  components: {
    MutiUploadPdf, MutiUploadLook
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      tableFormat: 'YYYY-MM-DD',
      coreVisible: false,
      xklxReq: false,
      loading: false,
      btnType: '',
      xlList: [],
      xklxList: [],
      formData: {
        id: '',
        yxname: '', // 院校
        xlxw: '', // 学历/学位
        srtdat: '', // 起始时间
        enddat: '', // 终止时间
        xkzy1: '', // 学科专业1
        xkzy2: '', // 学科专业2
        xklx: '', // 学科类型
        yjfx: '', // 研究方向
        dsxx: '' // 导师信息
      },
      formDataRules: {
        yxname: [
          { required: true, message: '请输入院校', trigger: 'change' }
        ],
        xlxw: [
          { required: true, message: '请输入学历/学位', trigger: 'change' }
        ],
        srtdat: [
          { required: true, message: '请选择起始时间', trigger: 'change' }
        ],
        enddat: [
          { required: true, message: '请选择终止时间', trigger: 'change' }
        ],
        xkzy1: [
          { required: true, message: '请选择学科专业1', trigger: 'change' }
        ],
        xklx: [
          { required: this.xklxReq, validator: this.xklxCheck, trigger: 'change' }
        ]
      },
      layout: {
        labelCol: { span: 5 },
        wrapperCol: { span: 18 }
      },
      layout2: {
        labelCol: { span: 10 },
        wrapperCol: { span: 12 }
      },
      layout3: {
        labelCol: { span: 8 },
        wrapperCol: { span: 14 }
      },
      columns: [{
        title: '院校',
        dataIndex: 'yxname',
        fixed: 'left',
        width: 200
      },
      {
        title: '起始时间',
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
        fixed: 'left',
        width: 115
      },
      {
        title: '终止时间',
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
        fixed: 'left',
        width: 115
      },
      {
        title: '学历/学位',
        dataIndex: 'xlxw',
        customRender: (text, row, index) => {
          let option = this.xlList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        fixed: 'left',
        width: 110
      },
      {
        title: '学科专业1',
        dataIndex: 'xkzy1',
        width: 150
      },
      {
        title: '学科类型',
        dataIndex: 'xklx',
        customRender: (text, row, index) => {
          let option = this.xklxList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        width: 150
      },
      {
        title: '研究方向',
        dataIndex: 'yjfx',
        width: 200
      },
      {
        title: '导师信息',
        dataIndex: 'dsxx',
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
        width: 115
      }
      ]
    }
  },
  mounted () {
  },
  methods: {
    moment,
    resetForm () {
      this.formData.yxname = '' // 院校
      this.formData.xlxw = '' // 学历/学位
      this.formData.srtdat = '' // 起始时间
      this.formData.enddat = '' // 终止时间
      this.formData.xkzy1 = '' // 学科专业1
      this.formData.xkzy2 = '' // 学科专业2
      this.formData.xklx = '' // 学科类型
      this.formData.yjfx = '' // 研究方向
      this.formData.dsxx = '' // 导师信息
    },
    xlxwChange (v) {
      this.xklxTf(v)
    },
    xklxTf (xlxw) {
      if (xlxw === '10' || xlxw === '11') {
        this.xklxReq = true
        if (!this.formData.xklx) {
          this.$message.warning('请选择学科类型.')
        }
      } else {
        this.formData.xklx = '' // 学科类型
        this.xklxReq = false
      }
    },
    xklxCheck (rule, value, callback) {
      if (this.formData.xlxw === '10' || this.formData.xlxw === '11') {
        if (value) {
          callback()
        } else {
          callback(new Error('请选择学科类型'))
        }
      } else {
        callback()
      }
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.yxname = this.formData.yxname // 院校
          params.xlxw = this.formData.xlxw // 学历/学位
          params.srtdat = this.formData.srtdat // 起始时间
          params.enddat = this.formData.enddat // 终止时间
          params.xkzy1 = this.formData.xkzy1 // 学科专业1
          params.xkzy2 = this.formData.xkzy2 // 学科专业2
          params.xklx = this.formData.xklx // 学科类型
          params.yjfx = this.formData.yjfx // 研究方向
          params.dsxx = this.formData.dsxx // 导师信息
          this.loading = true
          this.$post('zpStaffInfo/editZpStaffEducation', {
            ...params
          }).then(() => {
            this.loading = false
            this.baseTime = new Date().getTime()
            this.fetch()
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleOk () {
      this.coreVisible = false
      this.baseTime = new Date().getTime()
      this.fetch()
    },
    setFieldValues (datas, id, dicts) {
      this.dataSource = datas
      this.xlList = dicts.xlList
      this.xklxList = dicts.xklxList
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffEducation', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
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
              this.openNotificationIcon('success', '操作提醒', '删除教育经历成功.')
              this.dataSource = []
              this.fetch()
            } else {
              this.openNotificationIcon('error', '操作提醒', '删除教育经历失败.')
            }
          })
        }
      } else {
        this.openNotificationIcon('error', '操作提醒', '删除教育经历失败,至少存在一条教育经历数据.')
      }
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        yxname: record.yxname, // 院校
        xlxw: record.xlxw, // 学历/学位
        srtdat: moment(record.srtdat), // 起始时间
        enddat: moment(record.enddat), // 终止时间
        xkzy1: record.xkzy1, // 学科专业1
        xkzy2: record.xkzy2, // 学科专业2
        xklx: record.xklx, // 学科类型
        yjfx: record.yjfx, // 研究方向
        dsxx: record.dsxx // 导师信息
      }
      this.xklxTf(this.formData.xlxw)
    },
    handleAdd () {
      this.$get('zpStaffInfo/getId').then((r) => {
        if (r.data.data.success === 1) {
          let id = r.data.data.data
          if (id !== null) {
            this.formData.id = id
            this.resetForm()
            this.coreVisible = true
            this.btnType = 'addBtn'
            this.xklxReq = false
          } else {
            this.openNotificationIcon('error', '操作提醒', '增加教育经历失败.')
          }
        } else {
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
