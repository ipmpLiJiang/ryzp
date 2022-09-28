<template>
  <div>
    <a-row>
      <a-col :span="21">
        <b><font style="font-size:16px;padding-right: 10px;">家庭成员</font></b>
      </a-col>
      <a-col :span="3" style="text-align:right">
        <a-button class="editable-add-btn" @click="handleAdd"> +增加家庭成员 </a-button>
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
          refTab="family"
          refType="family"
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
      :title="btnType === 'addBtn' ? '新增家庭成员' : '修改家庭成员'"
      :width="650"
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
          <a-form-model-item label="称谓" prop="wcname">
            <a-input
              placeholder="请输入称谓"
              v-model="formData.wcname"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="姓名" prop="xmname">
            <a-input
              placeholder="请输入姓名"
              v-model="formData.xmname"
              :maxLength="30"
            />
          </a-form-model-item>
          <a-form-model-item label="出生日期" prop="csdat">
            <a-date-picker
              style="width: 50%"
              v-model="formData.csdat"
              @change="handleDateChange"
            />
          </a-form-model-item>
          <a-form-model-item label="政治面貌" prop="zzmm">
            <a-select
              style="width: 50%"
              v-model="formData.zzmm"
              placeholder="请选择政治面貌"
            >
              <a-select-option v-for="xx in zzmmList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
          <a-form-model-item label="工作单位及职务" prop="gzdwjzw">
            <a-input
              placeholder="请输入工作单位及职务"
              v-model="formData.gzdwjzw"
              :maxLength="30"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-model-item label="附件">
          <mutiUpload-pdf
            :baseId="formData.id"
            refTab="family"
            refType="family"
          >
          </mutiUpload-pdf>
          </a-form-model-item>
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
  name: 'StaffFamily',
  components: {
    MutiUploadPdf, MutiUploadLook
  },
  data () {
    return {
      dataSource: [],
      zzmmList: [],
      staffId: '',
      baseTime: new Date().getTime(),
      coreVisible: false,
      loading: false,
      btnType: '',
      formData: {
        id: '',
        wcname: '', // 称谓
        xmname: '', // 姓名
        zzmm: '', // 政治面貌
        csdat: '', // 出生年月
        csdats: '',
        gzdwjzw: '' // 工作单位及职务
      },
      formDataRules: {
        wcname: [
          { required: true, message: '请输入称谓', trigger: 'change' }
        ],
        xmname: [
          { required: true, message: '请输入姓名', trigger: 'change' }
        ],
        zzmm: [
          { required: true, message: '请选择政治面貌', trigger: 'change' }
        ],
        csdat: [
          { required: true, message: '请选择出生年月', trigger: 'change' }
        ]
      },
      layout: {
        labelCol: { span: 5 },
        wrapperCol: { span: 18 }
      },
      columns: [{
        title: '称谓',
        dataIndex: 'wcname',
        fixed: 'left',
        width: 150
      },
      {
        title: '姓名',
        dataIndex: 'xmname',
        fixed: 'left',
        width: 150
      },
      {
        title: '政治面貌',
        dataIndex: 'zzmm',
        customRender: (text, row, index) => {
          let option = this.zzmmList.filter(item => item.code === text)[0]
          return option ? option.name === '未选择' ? '' : option.name : ''
        },
        width: 200
      },
      {
        title: '出生日期',
        dataIndex: 'csdats',
        width: 115
      },
      {
        title: '工作单位及职务',
        dataIndex: 'gzdwjzw',
        width: 300
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
      this.formData.wcname = '' // 称谓
      this.formData.xmname = '' // 姓名
      this.formData.zzmm = '' // 政治面貌
      this.formData.csdat = '' // 出生年月
      this.formData.csdats = '' // 出生年月
      this.formData.gzdwjzw = '' // 工作单位及职务
    },
    // gzdwjzwCheck (rule, value, callback) {
    //   if (value) {
    //     callback()
    //   } else {
    //     callback(new Error('请输入工作单位及职务'))
    //   }
    // },
    editSubmit () {
      this.$refs.formData.validate(valid => {
        if (valid) {
          let params = {}
          params.id = this.formData.id
          params.staffId = this.staffId
          params.wcname = this.formData.wcname // 称谓
          params.xmname = this.formData.xmname // 姓名
          params.zzmm = this.formData.zzmm // 政治面貌
          params.csdat = this.formData.csdat // 出生年月
          params.csdats = this.formData.csdats // 出生年月
          params.gzdwjzw = this.formData.gzdwjzw // 工作单位及职务
          this.loading = true
          this.$post('zpStaffInfo/editZpStaffFamily', {
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
      this.zzmmList = dicts.zzmmList
      this.staffId = id
    },
    getFieldValues () {
      return this.dataSource
    },
    fetch () {
      this.$get('zpStaffInfo/findStaffFamily', {staffId: this.staffId}).then((r) => {
        if (r.data.data.success === 1) {
          this.dataSource = r.data.data.data
          this.coreVisible = false
        } else {
          this.openNotificationIcon('error', '操作提醒', '获取家庭成员失败.')
        }
      })
    },
    onDelete (key) {
      const newData = [...this.dataSource]
      if (newData.length > 1) {
        const target = newData.filter(item => key === item.id)[0]
        if (target) {
          this.$get('zpStaffInfo/del', { 'staffId': this.staffId, 'id': key, 'type': 'F' }).then((r) => {
            if (r.data.data.success === 1) {
              this.openNotificationIcon('success', '操作提醒', '删除家庭成员成功.')
              this.dataSource = []
              this.fetch()
            } else {
              this.openNotificationIcon('error', '操作提醒', '删除家庭成员失败.')
            }
          })
        }
      } else {
        this.openNotificationIcon('error', '操作提醒', '删除失败,至少存在一条家庭成员数据.')
      }
    },
    handleDateChange (value, dateString) {
      this.formData.csdats = dateString
    },
    handleEdit (record) {
      this.coreVisible = true
      this.btnType = 'editBtn'
      this.formData = {
        id: record.id,
        wcname: record.wcname, // 称谓
        xmname: record.xmname, // 姓名
        zzmm: record.zzmm, // 政治面貌
        csdat: moment(record.csdat), // 出生年月
        csdats: record.csdats, // 出生年月
        gzdwjzw: record.gzdwjzw // 工作单位及职务
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
            this.openNotificationIcon('error', '操作提醒', '增加家庭成员失败.')
          }
        } else {
          this.openNotificationIcon('error', '操作提醒', '增加家庭成员失败或异常.')
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
