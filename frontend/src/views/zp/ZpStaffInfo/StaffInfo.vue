<template>
  <a-form :form="form" class="formsty">
    <table class="formtab">
      <tr>
        <td colspan="15" class="tdTitle">
          <h2>基本信息</h2>
          <h4>Brief introduction</h4>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="td13">
          姓名<font class="fontColor">*(只读)</font>
        </td>
        <td colspan="3" class="td25">
          <a-form-item>
            <a-input
              placeholder="请输入姓名"
              :readOnly="true"
              v-decorator="[
                'ryname',
                { rules: [{ required: true, message: '姓名不能为空' }] },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2" class="td13">性别<font class="fontColor">*</font></td>
        <td colspan="5" class="td30">
          <a-form-item>
            <a-radio-group default-value="0" v-decorator="['sex']">
              <a-radio value="0"> 男 </a-radio>
              <a-radio value="1"> 女 </a-radio>
            </a-radio-group>
          </a-form-item>
        </td>
        <td rowspan="4" colspan="3">
          <a-row type="flex" align="middle">
            <a-col :span="1"> </a-col>
            <a-col :span="22">
              <uploadImg-file :baseId="staffInfo.id" :baseTime="baseTime">
              </uploadImg-file>
            </a-col>
            <a-col :span="1"> </a-col>
          </a-row>
        </td>
      </tr>
      <tr>
        <td colspan="2">出生日期<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-date-picker
              @change="csdatChange"
              v-decorator="[
                'csdat',
                { rules: [{ required: true, message: '出生年月不能为空' }] },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">身份证号码<font class="fontColor">*(只读)</font></td>
        <td colspan="5">
          <a-form-item>
            <a-input
              placeholder="请输入身份证号码"
              :maxLength="20"
              :readOnly="true"
              v-decorator="[
                'idnumber',
                {
                  rules: [
                    {
                      required: true,
                      max: 20,
                      message: '身份证号码不能为空, 长度不能超过20个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">籍贯<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入籍贯"
              :maxLength="30"
              v-decorator="[
                'zhrjg',
                {
                  rules: [
                    {
                      required: true,
                      max: 30,
                      message: '籍贯不能为空, 长度不能超过30个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">健康状态<font class="fontColor">*</font></td>
        <td colspan="5">
          <a-form-item>
            <a-input
              placeholder="请输入健康状态"
              :maxLength="20"
              v-decorator="[
                'jkzt',
                {
                  rules: [
                    {
                      required: true,
                      max: 20,
                      message: '健康状态不能为空, 长度不能超过20个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">身高cm<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input-number
              placeholder="请输入身高"
              :max="300"
              style="width: 100%"
              v-decorator="[
                'zhrsg',
                {
                  rules: [
                    {
                      required: true,
                      message: '身高不能为空',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">体重kg<font class="fontColor">*</font></td>
        <td colspan="5">
          <a-form-item>
            <a-input-number
              placeholder="请输入体重"
              :max="200"
              style="width: 100%"
              v-decorator="[
                'zhrtz',
                {
                  rules: [
                    {
                      required: true,
                      message: '体重不能为空',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">血型<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择血型"
              v-decorator="[
                'zhrxx',
                { rules: [{ required: true, message: '血型不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.xuexList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2">民族<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择民族"
              showSearch
              v-decorator="[
                'zhrmz',
                { rules: [{ required: true, message: '民族不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.mzList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2" style="width: 13%">
          是否服从调剂<font class="fontColor">*</font>
        </td>
        <td colspan="3">
          <a-form-item>
            <a-radio-group default-value="1" v-decorator="['isfcdj']">
              <a-radio value="1"> 是 </a-radio>
              <a-radio value="0"> 否 </a-radio>
            </a-radio-group>
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">政治面貌<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择政治面貌"
              v-decorator="[
                'zzmm',
                { rules: [{ required: true, message: '政治面貌不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.zzmmList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2">婚姻状况<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择婚姻状况"
              v-decorator="[
                'hyzt',
                { rules: [{ required: true, message: '婚姻状况不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.hyztList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2">子女个数<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input-number
              placeholder="请输入子女个数"
              :max="10"
              style="width: 100%"
              v-decorator="[
                'zngs',
                {
                  rules: [
                    {
                      required: true,
                      message: '子女个数不能为空',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">第一志愿科室<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入第一志愿科室"
              :maxLength="30"
              v-decorator="[
                'zyks1',
                {
                  rules: [
                    {
                      required: true,
                      max: 30,
                      message: '第一志愿科室不能为空, 长度不能超过30个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">第二志愿科室<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入第二志愿科室"
              :maxLength="50"
              v-decorator="[
                'zyks2',
                {
                  rules: [
                    {
                      required: true,
                      max: 50,
                      message: '第二志愿科室不能为空, 长度不能超过50个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">最高学历<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择最高学历"
              v-decorator="[
                'zgxl',
                { rules: [{ required: true, message: '最高学历不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.xlList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">外语水平<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择外语水平"
              v-decorator="[
                'wysp',
                { rules: [{ required: true, message: '外语水平不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.wyspList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2">外语水平分数<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input-number
              placeholder="请输入外语水平分数"
              style="width: 100%"
              v-decorator="[
                'wyspfs',
                {
                  rules: [
                    {
                      required: true,
                      message: '外语水平分数不能为空',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">计算机水平<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择计算机水平"
              v-decorator="[
                'jsjsp',
                { rules: [{ required: true, message: '计算机水平不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.jsjspList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">电子邮箱<font class="fontColor">*(只读)</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入邮箱"
              :readOnly="true"
              v-decorator="[
                'email',
                { rules: [{ required: true, message: '邮箱不能为空' }] },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">手机号码<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入手机号码"
              :maxLength="11"
              v-decorator="[
                'tel',
                {
                  rules: [
                    {
                      pattern: /^1[3|4|5|7|8][0-9]\d{8}$/,
                      message: '请输入正确的手机号码',
                    },
                    {
                      required: true,
                      max: 11,
                      message: '手机号码不能为空, 长度不能超过11个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">微信号<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入微信号"
              :maxLength="20"
              v-decorator="[
                'wechatNo',
                {
                  rules: [
                    {
                      required: true,
                      max: 20,
                      message: '微信号不能为空, 长度不能超过20个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">家庭住址<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入家庭住址"
              v-decorator="[
                'jtzz',
                { rules: [{ required: true, message: '家庭住址不能为空' }] },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">户籍地址<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入户籍地址"
              :maxLength="20"
              v-decorator="[
                'hjdz',
                {
                  rules: [
                    {
                      required: true,
                      max: 20,
                      message: '户籍地址不能为空, 长度不能超过20个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">现居住址<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入现居住址"
              :maxLength="20"
              v-decorator="[
                'xjdz',
                {
                  rules: [
                    {
                      required: true,
                      max: 20,
                      message: '现居住址不能为空, 长度不能超过20个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">紧急联系人<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入紧急联系人"
              v-decorator="[
                'jjlxr',
                { rules: [{ required: true, message: '紧急联系人不能为空' }] },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">联系人号码<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-input
              placeholder="请输入联系人号码"
              :maxLength="11"
              v-decorator="[
                'lxrtel',
                {
                  rules: [
                    {
                      pattern: /^1[3|4|5|7|8][0-9]\d{8}$/,
                      message: '请输入正确的联系人号码',
                    },
                    {
                      required: true,
                      max: 11,
                      message: '联系人号码不能为空, 长度不能超过11个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
        <td colspan="2">是否医师资格证<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-radio-group default-value="1" v-decorator="['isyszgz']">
              <a-radio value="1"> 是 </a-radio>
              <a-radio value="0"> 否 </a-radio>
            </a-radio-group>
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">职业类型<font class="fontColor">*</font></td>
        <td colspan="3">
          <a-form-item>
            <a-select
              style="width: 100%"
              placeholder="请选择职业类型"
              v-decorator="[
                'zylx',
                { rules: [{ required: true, message: '职业类型不能为空' }] },
              ]"
            >
              <a-select-option v-for="xx in dicts.zylxList" :key="xx.code">
                {{ xx.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </td>
        <td colspan="2">
          毕业时是否取得住院医师规范化培训合格证<font class="fontColor">*</font>
        </td>
        <td colspan="3">
          <a-form-item>
            <a-radio-group default-value="0" v-decorator="['isbysqdzyys']">
              <a-radio value="1"> 是 </a-radio>
              <a-radio value="0"> 否 </a-radio>
            </a-radio-group>
          </a-form-item>
        </td>
        <td colspan="2">
          硕士阶段是否四证合一<font class="fontColor">*</font>
        </td>
        <td colspan="3">
          <a-form-item>
            <a-radio-group default-value="0" v-decorator="['isssjdszhy']">
              <a-radio value="1"> 是 </a-radio>
              <a-radio value="0"> 否 </a-radio>
            </a-radio-group>
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">有无既往病史<font class="fontColor">*</font></td>
        <td colspan="13">
          <a-form-item>
            <a-input
              placeholder="请输入有无既往病史"
              :maxLength="50"
              v-decorator="[
                'ywjwbs',
                {
                  rules: [
                    {
                      required: true,
                      max: 50,
                      message: '有无既往病史不能为空, 长度不能超过50个字符',
                    },
                  ],
                },
              ]"
            />
          </a-form-item>
        </td>
      </tr>
      <tr>
        <td colspan="2">个人简历<font class="fontColor">*(.pdf)</font></td>
        <td colspan="3">
          <mutiUpload-pdf
            :baseId="staffInfo.id"
            :baseTime="baseTime"
            refTab="staffInfo"
            refType="staff"
          >
          </mutiUpload-pdf>
        </td>
        <td colspan="10"></td>
      </tr>
    </table>
    <br />
    <!-- 家庭成员 -->
    <staff-family ref="staffFamily"> </staff-family>
    <!-- 学历 -->
    <staff-education ref="staffEducation"> </staff-education>
    <br />
    <!-- 工作经历 -->
    <staff-work ref="staffWork"> </staff-work>
    <br />
    <!-- 项目信息 -->
    <staff-project ref="staffProject"> </staff-project>
    <br />
    <!-- 文章信息 -->
    <staff-essay ref="staffEssay"> </staff-essay>
    <br />
    <!-- 在校获奖情况 -->
    <staff-award ref="staffAward"> </staff-award>
    <a-row style="padding: 15px 0px">
      <a-col>
        <b>
          <font style="font-size: 16px">自我介绍</font>
        </b>
        (限500字)
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="24">
        <a-form-item>
          <a-textarea
            placeholder="请输入自我介绍"
            :maxLength="500"
            v-decorator="[
              'zwjs',
              { rules: [{ required: false, message: '自我介绍不能为空' }] },
            ]"
            :rows="6"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row style="padding: 15px 0px">
      <a-col>
        <b>
          <font style="font-size: 16px; padding-right: 10px"
            >在校获奖情况(院级以上)</font
          >
        </b>
        (限500字)
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="24">
        <a-form-item>
          <a-textarea
            placeholder="请输入在校获奖情况(院级以上)"
            :maxLength="500"
            v-decorator="[
              'zxhjqk',
              {
                rules: [
                  {
                    required: true,
                    message: '在校获奖情况(院级以上)不能为空',
                  },
                ],
              },
            ]"
            :rows="6"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-affix :offsetBottom="0">
      <div class="formbutton">
        <a-button type="primary" @click="handleSubmit" :loading="loading">
          保存简历
        </a-button>
        <a
          v-if="staffInfo.id === '' ? false : true"
          style="padding-left: 20px"
          @click="showLook"
          >浏览简历</a
        >
        <br />
        <font class="fontColor">{{ labmssage }}</font>
      </div>
    </a-affix>
    <a-modal
      :maskClosable="false"
      :footer="null"
      v-model="lookVisible"
      width="85%"
      title="浏览简历"
      @ok="handleLookOk"
    >
      <staffInfo-look ref="staffInfoLook" @close="closeLook"> </staffInfo-look>
    </a-modal>
  </a-form>
</template>

<script>
import moment from 'moment'
import StaffFamily from './StaffFamily'
import StaffEducation from './StaffEducation'
import StaffWork from './StaffWork'
import StaffEssay from './StaffEssay'
import StaffProject from './StaffProject'
import StaffAward from './StaffAward'
import UploadImgFile from '../../common/UploadImgFile'
import MutiUploadFile from '../../common/MutiUploadFile'
import MutiUploadPdf from '../../common/MutiUploadPdf'
import StaffInfoLook from '../../common/StaffInfoLook'
const formItemLayout = {
  labelCol: {
    span: 8
  },
  wrapperCol: {
    span: 15,
    offset: 1
  }
}
export default {
  name: 'StaffInfo',
  components: {
    StaffInfoLook,
    StaffFamily,
    StaffEducation,
    StaffWork,
    StaffEssay,
    StaffProject,
    StaffAward,
    UploadImgFile,
    MutiUploadFile,
    MutiUploadPdf
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      queryParams: {},
      bordered: true,
      loading: false,
      lookVisible: false,
      csdats: '',
      labmssage: '简历保存成功后，请到招聘主页中进行简历投递.',
      baseTime: 10,
      dicts: {},
      dictData: {},
      staffInfo: {
        id: '',
        educations: [],
        works: [],
        essays: [],
        familys: [],
        projects: [],
        awards: []
      }
    }
  },
  mounted () {
    this.getDutyList()
    this.fetch()
  },
  methods: {
    moment,
    showLook () {
      this.lookVisible = true
      setTimeout(() => {
        this.$refs.staffInfoLook.setFieldValues(this.staffInfo.id)
      }, 200)
    },
    handleLookOk () {
      this.lookVisible = false
    },
    closeLook () {
      this.lookVisible = false
    },
    setFormValues ({
      ...zpStaffInfo
    }) {
      let fields = ['ryname', 'email', 'sex', 'csdat', 'idnumber', 'zhrjg', 'jkzt', 'zhrsg', 'zhrtz', 'zhrxx', 'zhrmz', 'isfcdj', 'zzmm', 'hyzt', 'zngs', 'zyks1', 'zyks2', 'zgxl', 'wysp', 'wyspfs', 'jsjsp', 'tel', 'wechatNo', 'jtzz', 'hjdz', 'xjdz', 'jjlxr', 'lxrtel', 'isyszgz', 'zylx', 'isbysqdzyys', 'isssjdszhy', 'ywjwbs', 'zwjs', 'zxhjqk']
      let fieldDates = ['csdat']
      Object.keys(zpStaffInfo).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (zpStaffInfo[key] !== null && zpStaffInfo[key] !== '') {
              obj[key] = moment(zpStaffInfo[key])
            } else {
              obj[key] = ''
            }
          } else {
            obj[key] = zpStaffInfo[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      if (!zpStaffInfo.zhrxx) {
        this.form.getFieldDecorator('zhrxx')
        this.form.setFieldsValue({ 'zhrxx': '' })
      }
      if (!zpStaffInfo.zhrmz) {
        this.form.getFieldDecorator('zhrmz')
        this.form.setFieldsValue({ 'zhrmz': 'HA' })
      }
      if (!zpStaffInfo.zzmm) {
        this.form.getFieldDecorator('zzmm')
        this.form.setFieldsValue({ 'zzmm': '13' })
      }
      if (!zpStaffInfo.hyzt) {
        this.form.getFieldDecorator('hyzt')
        this.form.setFieldsValue({ 'hyzt': '0' })
      }
      if (!zpStaffInfo.jsjsp) {
        this.form.getFieldDecorator('jsjsp')
        this.form.setFieldsValue({ 'jsjsp': '' })
      }
      if (!zpStaffInfo.wysp) {
        this.form.getFieldDecorator('wysp')
        this.form.setFieldsValue({ 'wysp': '' })
      }
      if (!zpStaffInfo.zylx) {
        this.form.getFieldDecorator('zylx')
        this.form.setFieldsValue({ 'zylx': '' })
      }
      this.staffInfo.id = zpStaffInfo.id
      this.baseTime = new Date().getTime()
    },
    getDutyList () {
      this.$get('comType/findDictList', {
        isDeletemark: 1
      }).then((r) => {
        if (r.data.success === 1) {
          this.dictData = r.data
          let wyspList = []
          let xuexList = []
          let hyztList = []
          let zylxList = []
          let zzmmList = []
          let mzList = []
          let jsjspList = []
          let xlList = []
          let xklxList = []
          let fbztList = []
          let brpmList = []
          let kwjbList = []
          let wxz = { code: '', name: '未选择' }
          wyspList.push(wxz)
          xuexList.push(wxz)
          hyztList.push(wxz)
          zylxList.push(wxz)
          zzmmList.push(wxz)
          mzList.push(wxz)
          xlList.push(wxz)
          jsjspList.push(wxz)
          xklxList.push(wxz)
          fbztList.push(wxz)
          brpmList.push(wxz)
          kwjbList.push(wxz)
          if (r.data.wyspList) {
            r.data.wyspList.forEach(function (k) {
              wyspList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.xuexList) {
            r.data.xuexList.forEach(function (k) {
              xuexList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.hyztList) {
            r.data.hyztList.forEach(function (k) {
              hyztList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.zylxList) {
            r.data.zylxList.forEach(function (k) {
              zylxList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.zzmmList) {
            r.data.zzmmList.forEach(function (k) {
              zzmmList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.mzList) {
            r.data.mzList.forEach(function (k) {
              mzList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.jsjspList) {
            r.data.jsjspList.forEach(function (k) {
              jsjspList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.xlList) {
            r.data.xlList.forEach(function (k) {
              xlList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.xklxList) {
            r.data.xklxList.forEach(function (k) {
              xklxList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.fbztList) {
            r.data.fbztList.forEach(function (k) {
              fbztList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.brpmList) {
            r.data.brpmList.forEach(function (k) {
              brpmList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          if (r.data.kwjbList) {
            r.data.kwjbList.forEach(function (k) {
              kwjbList.push({ code: k.ctCode, name: k.ctName })
            })
          }
          this.dicts.wyspList = wyspList
          this.dicts.xuexList = xuexList
          this.dicts.hyztList = hyztList
          this.dicts.zylxList = zylxList
          this.dicts.zzmmList = zzmmList
          this.dicts.mzList = mzList
          this.dicts.jsjspList = jsjspList
          this.dicts.xlList = xlList
          this.dicts.xklxList = xklxList
          this.dicts.fbztList = fbztList
          this.dicts.brpmList = brpmList
          this.dicts.kwjbList = kwjbList
        }
      })
    },
    fetch () {
      this.$get('zpStaffInfo').then((r) => {
        if (r.data.data.success === 1) {
          let staffInfoData = r.data.data.data
          let csdats = null
          if (staffInfoData.csdats !== null) {
            csdats = moment(staffInfoData.csdats).format('YYYY-MM-DD')
          }
          this.csdats = csdats
          let sInfo = {
            id: staffInfoData.id,
            ryname: staffInfoData.ryname,
            email: staffInfoData.email,
            sex: staffInfoData.sex === null ? '0' : staffInfoData.sex.toString(),
            csdat: csdats,
            idnumber: staffInfoData.idnumber,
            zhrjg: staffInfoData.zhrjg,
            jkzt: staffInfoData.jkzt,
            zhrsg: staffInfoData.zhrsg,
            zhrtz: staffInfoData.zhrtz,
            zhrxx: staffInfoData.zhrxx,
            zhrmz: staffInfoData.zhrmz,
            isfcdj: staffInfoData.isfcdj === null ? '0' : staffInfoData.isfcdj.toString(),
            zzmm: staffInfoData.zzmm,
            hyzt: staffInfoData.hyzt,
            zngs: staffInfoData.zngs,
            zyks1: staffInfoData.zyks1,
            zyks2: staffInfoData.zyks2,
            zgxl: staffInfoData.zgxl,
            wysp: staffInfoData.wysp,
            wyspfs: staffInfoData.wyspfs,
            jsjsp: staffInfoData.jsjsp,
            tel: staffInfoData.tel,
            wechatNo: staffInfoData.wechatNo,
            jtzz: staffInfoData.jtzz,
            hjdz: staffInfoData.hjdz,
            xjdz: staffInfoData.xjdz,
            jjlxr: staffInfoData.jjlxr,
            lxrtel: staffInfoData.lxrtel,
            isyszgz: staffInfoData.isyszgz === null ? '0' : staffInfoData.isyszgz.toString(),
            zylx: staffInfoData.zylx,
            isbysqdzyys: staffInfoData.isbysqdzyys === null ? '0' : staffInfoData.isbysqdzyys.toString(),
            isssjdszhy: staffInfoData.isssjdszhy === null ? '0' : staffInfoData.isssjdszhy.toString(),
            ywjwbs: staffInfoData.ywjwbs,
            zwjs: staffInfoData.zwjs,
            zxhjqk: staffInfoData.zxhjqk
          }
          // console.log(sInfo)
          this.setFormValues(sInfo)
          this.$refs.staffFamily.setFieldValues(staffInfoData.familys, staffInfoData.id, this.dicts)
          this.$refs.staffEducation.setFieldValues(staffInfoData.educations, staffInfoData.id, this.dicts)
          this.$refs.staffWork.setFieldValues(staffInfoData.works, staffInfoData.id, this.dicts)
          this.$refs.staffProject.setFieldValues(staffInfoData.projects, staffInfoData.id)
          this.$refs.staffEssay.setFieldValues(staffInfoData.essays, staffInfoData.id, this.dicts)
          this.$refs.staffAward.setFieldValues(staffInfoData.awards, staffInfoData.id)
        } else {
          // this.$message.error('获取个人简历失败.')
          this.openNotificationIcon('error', '操作提醒', '获取个人简历失败.')
        }
      })
    },
    csdatChange (date, dateString) {
      this.csdats = dateString
    },
    handleSubmit () {
      // let d = new Date('1991-04-15 09:09:36')
      // console.log(d)
      this.form.validateFields((err, values) => {
        if (!err) {
          let zpStaffInfo = this.form.getFieldsValue()
          zpStaffInfo.id = this.staffInfo.id
          this.staffInfo = zpStaffInfo
          this.staffInfo.csdats = this.csdats
          debugger
          let f = this.$refs.staffFamily.getFieldValues()
          if (f.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '家庭成员未填写数据,请确保存在一条家庭成员.')
            return
          }
          let e = this.$refs.staffEducation.getFieldValues()
          if (e.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '教育经历未填写数据,请确保存在一条教育经历.')
            return
          }
          let w = this.$refs.staffWork.getFieldValues()
          if (w.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '工作经历未填写数据,请确保存在一条工作经历.')
            return
          }
          let p = this.$refs.staffProject.getFieldValues()
          if (p.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '项目信息未填写数据,请确保存在一条项目信息.')
            return
          }
          let y = this.$refs.staffEssay.getFieldValues()
          if (y.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '文章信息未填写数据,请确保存在一条文章信息.')
            return
          }
          let a = this.$refs.staffAward.getFieldValues()
          if (a.length === 0) {
            this.openNotificationIcon('warning', '操作提醒', '获奖情况未填写数据,请确保存在一条获奖情况.')
            return
          }
          this.loading = true
          this.$put('zpStaffInfo/saveStaffInfo', {
            data: JSON.stringify(this.staffInfo)
          }).then((r) => {
            if (r.data.data.success === 1) {
              // this.$message.success('提交个人简历成功.')
              this.openNotificationIcon('success', '操作提醒', '保存个人简历成功.')
            } else {
              // this.$message.error('提交个人简历失败.')
              this.openNotificationIcon('error', '操作提醒', '保存个人简历失败.')
            }
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
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

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style><style lang="less" scoped>
.formsty {
  width: 100%;
  padding: 12px 30px;
  background: #fff;
}

.formtab {
  border-collapse: collapse;
  width: 100%;
  margin: 0 auto;
  padding: 8px 6px;
}

.formtab {
  tr td {
    padding: 10px;
    border: 1px solid #ccc;
  }
  .td13 {
    width: 12%;
  }
  .td25 {
    width: 23%;
  }
  .td30 {
    width: 35%;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}

.fontColor {
  color: red;
}

.formbutton {
  width: 100%;
  padding: 20px;
  background: #fff;
  text-align: center;
  border-top: 1px solid #67c799;

  button {
    width: 25%;
  }
}
</style>
