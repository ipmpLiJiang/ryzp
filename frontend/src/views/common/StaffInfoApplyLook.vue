<template>
  <div class="formsty">
    <table class="formtab">
      <tr>
        <td colspan="15" class="tdTitle">
          <h2>基本信息</h2>
          <h4>Brief introduction</h4>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="td13">
          姓名<font class="fontColor">*</font>
        </td>
        <td colspan="3" class="td25">
          {{ staffInfo.ryname }}
        </td>
        <td colspan="2" class="td14">性别<font class="fontColor">*</font></td>
        <td colspan="5" class="td30">
          {{ staffInfo.sex === 0 ? '男' : '女' }}
        </td>
        <td rowspan="4" colspan="3">
          <uploadImg-look
            :baseId="staffInfo.id"
            :baseTime="baseTime"
          >
          </uploadImg-look>
        </td>
      </tr>
      <tr>
        <td colspan="2">出生日期<font class="fontColor">*</font></td>
        <td colspan="3">
          {{ setDateFormat(staffInfo.csdats) }}
        </td>
        <td colspan="2">身份证号码<font class="fontColor">*</font></td>
        <td colspan="5">
          {{ staffInfo.idnumber }}
        </td>
      </tr>
      <tr>
        <td colspan="2">籍贯<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zhrjg }}</td>
        <td colspan="2">健康状态<font class="fontColor">*</font></td>
        <td colspan="5">{{ staffInfo.jkzt }}</td>
      </tr>
      <tr>
        <td colspan="2">身高cm<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zhrsg }}</td>
        <td colspan="2">体重kg<font class="fontColor">*</font></td>
        <td colspan="5">{{ staffInfo.zhrtz }}</td>
      </tr>
      <tr>
        <td colspan="2">血型<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zhrxxName }}</td>
        <td colspan="2">民族<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zhrmzName }}</td>
        <td colspan="2" style="width:12%">是否服从调剂<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.isfcdj === 0 ? '否' : '是' }}</td>
      </tr>
      <tr>
        <td colspan="2">政治面貌<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zzmmName }}</td>
        <td colspan="2">婚姻状况<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.hyztName }}</td>
        <td colspan="2">子女个数<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zngs }}</td>
      </tr>
      <tr>
        <td colspan="2">第一志愿科室<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zyks1 }}</td>
        <td colspan="2">第二志愿科室<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zyks2 }}</td>
        <td colspan="2">最高学历<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.zgxl }}</td>
      </tr>
      <tr>
        <td colspan="2">外语水平<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.wyspName }}</td>
        <td colspan="2">外语水平分数<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.wyspfs }}</td>
        <td colspan="2">计算机水平<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.jsjspName }}</td>
      </tr>
      <tr>
        <td colspan="2">电子邮箱<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.email }}</td>
        <td colspan="2">手机号码<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.tel }}</td>
        <td colspan="2">微信号<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.wechatNo }}</td>
      </tr>
      <tr>
        <td colspan="2">家庭住址<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.jtzz }}</td>
        <td colspan="2">户籍地址<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.hjdz }}</td>
        <td colspan="2">现居住址<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.xjdz }}</td>
      </tr>
      <tr>
        <td colspan="2">紧急联系人<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.jjlxr }}</td>
        <td colspan="2">联系人号码<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.lxrtel }}</td>
        <td colspan="2">是否医师资格证<font class="fontColor">*</font></td>
        <td colspan="3">{{ staffInfo.isyszgz === 0 ? '否' : '是' }}</td>
      </tr>
      <tr>
        <td colspan="2">职业类型<font class="fontColor">*</font></td>
        <td colspan="3">{{staffInfo.zylxName}}</td>
        <td colspan="2">
          毕业时是否取得住院医师规范化培训合格证<font class="fontColor">*</font>
        </td>
        <td colspan="3">{{ staffInfo.isbysqdzyys === 0 ? '否' : '是' }}</td>
        <td colspan="2">
          硕士阶段是否四证合一<font class="fontColor">*</font>
        </td>
        <td colspan="3">{{ staffInfo.isssjdszhy === 0 ? '否' : '是' }}</td>
      </tr>
      <tr>
        <td colspan="2">有无既往病史<font class="fontColor">*</font></td>
        <td colspan="13">{{ staffInfo.ywjwbs }}</td>
      </tr>
      <tr>
        <td colspan="2">个人简历<font class="fontColor">*(.pdf)</font></td>
        <td colspan="3">
          <mutiUpload-look
            :baseId="staffInfo.id"
            :baseTime="baseTime"
            refTab="staffInfo"
            refType="staff"
          >
          </mutiUpload-look>
        </td>
        <td colspan="10"></td>
      </tr>
    </table>
    <br />
    <!-- 学历 -->
    <a-row type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size: 16px">教育经历</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="e in staffInfo.educations" :key="e.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="10">
          院&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校：
        </a-col>
        <a-col flex="90">
          {{ e.yxname }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 起始时间： </a-col>
        <a-col flex="40">
          {{ setDateFormat(e.srtdat) }}
        </a-col>
        <a-col flex="10"> 终止时间： </a-col>
        <a-col flex="40">
          {{ setDateFormat(e.enddat) }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 学历/学位： </a-col>
        <a-col flex="40">
          {{ e.xlxw }}
        </a-col>
        <a-col flex="10"> 导师信息： </a-col>
        <a-col flex="40">
          {{ e.dsxx }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 所学专业： </a-col>
        <a-col flex="40">
          {{ e.sxzy }}
        </a-col>
        <a-col flex="10"> 研究方向： </a-col>
        <a-col flex="40">
          {{ e.yjfx }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 附件材料： </a-col>
        <a-col flex="90">
          <mutiUpload-look
            :baseId="e.id"
            :baseTime="baseTime"
            refTab="education"
            refType="education"
          >
          </mutiUpload-look>
        </a-col>
      </a-row>
    </a-row>
    <br />
    <!-- 工作经历 -->
    <a-row type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size: 16px">工作经历</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="w in staffInfo.works" :key="w.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="10"> 工作单位： </a-col>
        <a-col flex="90">
          {{ w.wkdw }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 起始时间： </a-col>
        <a-col flex="40">
          {{ setDateFormat(w.srtdat) }}
        </a-col>
        <a-col flex="10"> 终止时间： </a-col>
        <a-col flex="40">
          {{ setDateFormat(w.enddat) }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10"> 工作职务： </a-col>
        <a-col flex="40">
          {{ w.wkzw }}
        </a-col>
        <a-col flex="10"> 导师信息： </a-col>
        <a-col flex="40">
          {{ w.dsxx }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          学历：
        </a-col>
        <a-col flex="40">
          {{w.wkxl}}
        </a-col>
        <a-col flex="10">
          备注：
        </a-col>
        <a-col flex="40">
          {{w.remark}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          附件材料：
        </a-col>
        <a-col flex="90">
          <mutiUpload-look
            :baseId="w.id"
            :baseTime="baseTime"
            refTab="work"
            refType="work"
          >
          </mutiUpload-look>
        </a-col>
      </a-row>
    </a-row>
    <br />
    <!-- 著作论文 -->
    <a-row type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size: 16px">文章信息</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="y in staffInfo.essays" :key="y.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="15"> 文章名称： </a-col>
        <a-col flex="90">
          {{ y.lwlzmc }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15"> 作者名称： </a-col>
        <a-col flex="40">
          {{ y.zzname }}
        </a-col>
        <a-col flex="10"> 发表期刊： </a-col>
        <a-col flex="40">
          {{ y.fbqk }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15"> 发表或出版年度： </a-col>
        <a-col flex="40">
          {{ y.fbcbny }}
        </a-col>
        <a-col flex="10"> 收录情况： </a-col>
        <a-col flex="40">
          {{ y.slqk }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15"> 影响因子： </a-col>
        <a-col flex="40">
          {{ y.yxyz }}
        </a-col>
        <a-col flex="10"> JCR分区： </a-col>
        <a-col flex="40">
          {{ y.jcrfq }}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15"> 他引次数： </a-col>
        <a-col flex="40">
          {{ y.tycs }}
        </a-col>
        <a-col flex="10"> 附件材料： </a-col>
        <a-col flex="40">
          <mutiUpload-look
            :baseId="y.id"
            :baseTime="baseTime"
            refTab="essay"
            refType="essay"
          >
          </mutiUpload-look>
        </a-col>
      </a-row>
    </a-row>
    <br />
    <a-row type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size: 16px">在校获奖情况(院级以上)</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol">
      <a-col :span="24">
        {{ staffInfo.zxhjqk }}
      </a-col>
    </a-row>
    <br />
    <a-row v-if="staffInfo.comments" type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size: 16px">其他</font></b>
      </a-col>
    </a-row>
    <a-row v-if="staffInfo.comments" class="mxrowcol">
      <a-col :span="24">
        {{ staffInfo.comments }}
      </a-col>
    </a-row>
    <a-affix :offsetBottom="0">
      <div class="formbutton">
        <!-- v-if="applystate === 0 ? true : false" -->
        <a-button
          type="primary"
          @click="applyCk"
          :loading="loading"
          style="width: 10%; margin-right: 0.8rem"
          >更新查看</a-button
        >
        <!-- v-if="applystate === 0 || applystate === 1 || applystate === 2 ? true : false" -->
        <a-button
          type="primary"
          @click="applyTg"
          :loading="loading"
          style="width: 10%; margin-right: 0.8rem"
          >更新通过</a-button
        >
        <!-- v-if="applystate === 0 || applystate === 1 || applystate === 6 ? true : false" -->
        <a-button
          type="primary"
          @click="applyJJ"
          :loading="loading"
          style="width: 10%; margin-right: 0.8rem"
          >更新跟踪</a-button
        >
        <a-button @click="onClose" style="width: 15%; margin-right: 0.8rem"
          >返回列表</a-button
        >
      </div>
    </a-affix>
  </div>
</template>

<script>
import moment from 'moment'
import UploadImgLook from '../common/UploadImgLook'
import MutiUploadLook from '../common/MutiUploadLook'
export default {
  name: 'StaffInfoLook',
  components: {
    UploadImgLook, MutiUploadLook
  },
  data () {
    return {
      queryParams: {},
      bordered: true,
      baseTime: 10,
      loading: false,
      dataFormat: 'YYYY-MM-DD',
      applyId: '',
      applystate: 0,
      staffInfo: {
        id: '',
        educations: [],
        works: [],
        essays: []
      }
    }
  },
  mounted () {
  },
  methods: {
    moment,
    onClose () {
      this.$emit('close')
    },
    setDateFormat (date) {
      if (date !== '' && date !== null) {
        if (isNaN(date) && !isNaN(Date.parse(date))) {
          return moment(date).format(this.dataFormat)
        } else {
          return date
        }
      } else {
        return date
      }
    },
    applyCk () {
      let that = this
      this.$confirm({
        title: '确定更新已查看简历?',
        content: '当您点击确定按钮后，该简历更新为已查看状态',
        centered: true,
        onOk () {
          that.$put('zpStaffInfo/applyState', {
            applyId: that.applyId, state: 1
          }).then((r) => {
            if (r.data.data.success === 1) {
              // that.$message.success('更新已查看简历成功.')
              that.openNotificationIcon('success', '操作提醒', '更新已查看简历成功.')
              that.$emit('success')
            } else {
              // that.$message.error('更新已查看简历失败.')
              that.openNotificationIcon('error', '操作提醒', '更新已查看简历失败.')
            }
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    applyTg () {
      let that = this
      this.$confirm({
        title: '确定更新通过简历?',
        content: '当您点击确定按钮后，该简历更新为已通过状态',
        centered: true,
        onOk () {
          that.$put('zpStaffInfo/applyState', {
            applyId: that.applyId, state: 6
          }).then((r) => {
            if (r.data.data.success === 1) {
              // that.$message.success('通过简历成功.')
              that.openNotificationIcon('success', '操作提醒', '通过简历成功.')
              that.$emit('success')
            } else {
              // that.$message.error('通过简历失败.')
              that.openNotificationIcon('error', '操作提醒', '通过简历失败.')
            }
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    applyJJ () {
      let that = this
      this.$confirm({
        title: '确定更新跟踪简历?',
        content: '当您点击确定按钮后，该简历更新为跟踪状态',
        centered: true,
        onOk () {
          that.$put('zpStaffInfo/applyState', {
            applyId: that.applyId, state: 2
          }).then((r) => {
            if (r.data.data.success === 1) {
              // that.$message.success('跟踪简历成功.')
              that.openNotificationIcon('success', '操作提醒', '跟踪简历成功.')
              that.$emit('success')
            } else {
              // that.$message.error('跟踪简历失败.')
              that.openNotificationIcon('error', '操作提醒', '跟踪简历失败.')
            }
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
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
    },
    setFieldValues (staffId, applyId, applystate) {
      var today = new Date()
      this.applyId = applyId
      this.applystate = applystate
      this.$get('zpStaffInfo/staffInfoView', { staffId: staffId }).then((r) => {
        if (r.data.data.success === 1) {
          this.staffInfo = r.data.data.data
          this.baseTime = today.getTime()
        } else {
          // this.$message.error('获取个人简历失败.')
          this.openNotificationIcon('error', '操作提醒', '获取个人简历失败.')
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
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
    width: 13%;
  }
  .td14 {
    width: 15%;
  }
  .td25 {
    width: 22%;
  }
  .td30 {
    width: 34%;
  }
  .tdTitle {
    border: 0px;
    text-align: center;
  }
}
.fontColor {
  color: red;
}
.mxrow {
  padding: 10px;
  border: 1px solid #ccc;
}
.mxrowcol {
  padding: 10px;
  border-left: 1px solid #ccc;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}
.titlerowcol1 {
  background: #00bfff;
}
.titlerowcol2 {
  padding-left: 5px;
}
.contentrowtop {
  padding-left: 20px;
}
.contentrow {
  padding-top: 10px;
  padding-left: 20px;
}
.formbutton {
  width: 100%;
  padding: 20px 0px;
  background: #fff;
  text-align: center;
  border-top: 1px solid #67c799;
}
</style>
