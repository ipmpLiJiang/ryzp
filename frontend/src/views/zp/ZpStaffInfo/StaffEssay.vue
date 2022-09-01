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
      <template slot="operationFile" slot-scope="text, record">
        <mutiUpload-look
          :baseId="record.id"
          :baseTime="baseTime"
          refTab="essay"
          refType="essay"
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
          <a style="color:red" href="javascript:;">删除</a>
        </a-popconfirm>
      </template>
    </a-table>
    <a-modal
      class="ant-modal-nofooter"
      :footer="null"
      v-model="coreVisible"
      :title="btnType === 'addBtn' ? '新增文章信息' : '修改文章信息'"
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
          <a-form-model-item label="文章名称" prop="wzname">
            <a-input
              placeholder="请输入文章名称"
              v-model="formData.wzname"
              :maxLength="300"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="刊物级别" prop="kwjb" v-bind="layout2">
            <a-select
              v-model="formData.kwjb"
              placeholder="请选择刊物级别"
            >
              <a-select-option v-for="xx in kwjbList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="本人排名" prop="brpm" v-bind="layout3">
            <a-select
              v-model="formData.brpm"
              placeholder="请选择本人排名"
            >
              <a-select-option v-for="xx in brpmList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="出版时间" prop="cbdat" v-bind="layout2">
            <a-date-picker
              v-model="formData.cbdat"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="发布状态" prop="fbzt" v-bind="layout3">
            <a-select
              v-model="formData.fbzt"
              placeholder="请选择发布状态"
            >
              <a-select-option v-for="xx in fbztList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="出版刊物" prop="cbkw">
            <a-input
              placeholder="请输入出版刊物"
              v-model="formData.cbkw"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="刊号" prop="cbkh">
            <a-input
              placeholder="请输入刊号"
              v-model="formData.cbkh"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="影响因子" prop="yxyz">
            <a-input
              placeholder="请输入影响因子"
              v-model="formData.yxyz"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <mutiUpload-pdf
            :baseId="formData.id"
            refTab="essay"
            refType="essay"
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
import moment from 'moment'
import MutiUploadPdf from '../../common/MutiUploadPdf'
import MutiUploadLook from '../../common/MutiUploadLook'
export default {
  name: 'StaffEssay',
  components: {
    MutiUploadPdf, MutiUploadLook
  },
  data () {
    return {
      dataSource: [],
      staffId: '',
      baseTime: new Date().getTime(),
      fbztList: [],
      brpmList: [],
      kwjbList: [],
      tableFormat: 'YYYY-MM-DD',
      xlList: [],
      coreVisible: false,
      loading: false,
      btnType: '',
      formData: {
        id: '',
        wzname: '', // 文章名称
        kwjb: '', // 刊物级别
        brpm: '', // 本人排名
        cbdat: '', // 出版时间
        fbzt: '', // 发布状态
        cbkw: '', // 出版刊物
        yxyz: '' // 影响因子
      },
      formDataRules: {
        wzname: [
          { required: true, message: '请输入文章名称', trigger: 'change' }
        ],
        kwjb: [
          { required: true, message: '请输入刊物级别', trigger: 'change' }
        ],
        brpm: [
          { required: true, message: '请选择本人排名', trigger: 'change' }
        ],
        cbdat: [
          { required: true, message: '请选择出版时间', trigger: 'change' }
        ],
        cbkw: [
          { required: true, message: '请选择出版刊物', trigger: 'change' }
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
        title: '文章名称',
        dataIndex: 'wzname',
        fixed: 'left',
        width: 300
      },
      {
        title: '刊物级别',
        dataIndex: 'kwjb',
        customRender: (text, row, index) => {
          let option = this.kwjbList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        fixed: 'left',
        width: 100
      },
      {
        title: '本人排名',
        dataIndex: 'brpm',
        customRender: (text, row, index) => {
          let option = this.brpmList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        fixed: 'left',
        width: 110
      },
      {
        title: '出版时间',
        dataIndex: 'cbdat',
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
        width: 115
      },
      {
        title: '发布状态',
        dataIndex: 'fbzt',
        customRender: (text, row, index) => {
          let option = this.fbztList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        width: 100
      },
      {
        title: '出版刊物',
        dataIndex: 'cbkw',
        width: 110
      },
      {
        title: '刊号',
        dataIndex: 'cbkh',
        width: 110
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
      this.formData.wzname = '' // 文章名称
      this.formData.kwjb = '' // 刊物级别
      this.formData.brpm = '' // 本人排名
      this.formData.cbdat = '' // 出版时间
      this.formData.fbzt = '' // 发布状态
      this.formData.cbkw = '' // 出版刊物
      this.formData.cbkh = '' // 刊号
      this.formData.yxyz = '' // 影响因子
    },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.wzname = this.formData.wzname // 文章名称
          params.kwjb = this.formData.kwjb // 刊物级别
          params.brpm = this.formData.brpm // 本人排名
          params.cbdat = this.formData.cbdat // 出版时间
          params.fbzt = this.formData.fbzt // 发布状态
          params.cbkw = this.formData.cbkw // 出版刊物
          params.cbkh = this.formData.cbkh // 刊号
          params.yxyz = this.formData.yxyz // 影响因子

          this.loading = true
          this.$post('zpStaffInfo/editZpStaffEssay', {
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
      this.fbztList = dicts.fbztList
      this.brpmList = dicts.brpmList
      this.kwjbList = dicts.kwjbList
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffEssay', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
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
              this.openNotificationIcon('success', '操作提醒', '删除文章信息成功.')
              this.dataSource = []
              this.fetch()
            } else {
              this.openNotificationIcon('error', '操作提醒', '删除该文章信息失败.')
            }
          })
        }
      } else {
        this.openNotificationIcon('error', '操作提醒', '删除文章信息失败,至少存在一条文章信息数据.')
      }
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        wzname: record.wzname, // 文章名称
        kwjb: record.kwjb, // 刊物级别
        brpm: record.brpm, // 本人排名
        cbdat: moment(record.cbdat), // 出版时间
        fbzt: record.fbzt, // 发布状态
        cbkw: record.cbkw, // 出版刊物
        cbkh: record.cbkh, // 刊号
        yxyz: record.yxyz // 影响因子

      }
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
          } else {
            this.openNotificationIcon('error', '操作提醒', '增加文章信息失败.')
          }
        } else {
          this.openNotificationIcon('error', '操作提醒', '增加文章信息失败或异常.')
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
