<template>
  <div class="formsty">
    <table class="formtab">
      <tr>
        <td colspan="8" class="tdTitle">
          <h2>基本信息</h2>
        </td>
      </tr>
      <tr>
        <td class="td13">姓名/Name<font class="fontColor">*</font></td>
        <td class="td2" colspan="2">
          {{ staffInfo.ryname }}
        </td>
        <td class="td13">
          性别/Gender<font class="fontColor">*</font>
        </td>
        <td class="td4" colspan="2">
          {{ staffInfo.sex === 0 ? '男' : '女' }}
        </td>
        <td rowspan="4">
          <uploadImg-look
            :baseId="staffInfo.id"
            :baseTime="baseTime"
          >
          </uploadImg-look>
        </td>
      </tr>
      <tr>
        <td>出生日期/Birthday<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ setDateFormat(staffInfo.csdats) }}
        </td>
        <td>健康状态<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.jkzt }}
        </td>
      </tr>
      <tr>
        <td>籍贯<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.zhrjg }}
        </td>
        <td>现工作国家地区/Working country<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.ingzgjdq }}
        </td>
      </tr>
      <tr>
        <td>现任职单位/Company Abroad<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.indw }}
        </td>
        <td>现任职务/Current Position<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.inzw }}
        </td>
      </tr>
      <tr>
        <td>专业方向/Professional Diregtiog<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.zhrzyfx }}
        </td>
        <td style="width:15%">最高学历Highest Degree<font class="fontColor">*</font></td>
        <td style="width:15%">
          {{ staffInfo.zgxl }}
        </td>
        <td style="width:15%">是否医师资格证<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.isyszgz === 1 ? '是' : '否' }}
        </td>
      </tr>
      <tr>
        <td>第一志愿科室<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.zyks1 }}
        </td>
        <td>第二志愿科室<font class="fontColor">*</font></td>
        <td colspan="5">
          {{ staffInfo.zyks2 }}
        </td>
      </tr>
      <tr>
        <td>邮箱Email<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.email }}
        </td>
        <td>手机号码<font class="fontColor">*</font></td>
        <td>
          {{ staffInfo.tel }}
        </td>
        <td>微信号/Wechat<font class="fontColor">*</font></td>
        <td colspan="2">
          {{ staffInfo.wechatNo }}
        </td>
      </tr>
      <tr>
        <td>申请人类别Type<font class="fontColor">*</font></td>
        <td colspan="7">
          <a-checkbox v-model="staffInfo.isxsdtr">
            学术带头人
          </a-checkbox>
          <a-checkbox v-model="staffInfo.isyyrcxm">
            青年学者（已有人才项目）
          </a-checkbox>
          <a-checkbox v-model="staffInfo.issbhwyq">
            青年学者（申报海外优青）
          </a-checkbox>
          <a-checkbox v-model="staffInfo.iswrcxm">
            青年学者（无人才项目）
          </a-checkbox>
        </td>
      </tr>
      <tr>
        <td>个人简历/Resume<font class="fontColor">*</font></td>
        <td colspan="2">
          <mutiUpload-look
            :baseId="staffInfo.id"
            :baseTime="baseTime"
            refTab="staffInfo"
            refType="staff"
          >
          </mutiUpload-look>
        </td>
        <td colspan="5"></td>
      </tr>
    </table>
    <br />
    <!-- 学历 -->
    <a-row type="flex" class="mxrow">
      <a-col class="titlerowcol1" flex="5px"> </a-col>
      <a-col class="titlerowcol2" flex="auto">
        <b><font style="font-size:16px">教育经历</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="e in staffInfo.educations" :key="e.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="10">
          院&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校：
        </a-col>
        <a-col flex="90">
          {{e.yxname}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          起始时间：
        </a-col>
        <a-col flex="40">
          {{setDateFormat(e.srtdat)}}
        </a-col>
        <a-col flex="10">
          终止时间：
        </a-col>
        <a-col flex="40">
          {{setDateFormat(e.enddat)}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          学历：
        </a-col>
        <a-col flex="40">
          {{e.xlxw}}
        </a-col>
        <a-col flex="10">
          导师信息：
        </a-col>
        <a-col flex="40">
          {{e.dsxx}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          学科专业1：
        </a-col>
        <a-col flex="40">
          {{e.xkzy1}}
        </a-col>
        <a-col flex="10">
          学科专业2：
        </a-col>
        <a-col flex="40">
          {{e.xkzy2}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          研究方向：
        </a-col>
        <a-col flex="40">
          {{e.yjfx}}
        </a-col>
        <a-col flex="10">
          附件材料：
        </a-col>
        <a-col flex="40">
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
        <b><font style="font-size:16px">工作经历</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="w in staffInfo.works" :key="w.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="10">
          工作单位：
        </a-col>
        <a-col flex="90">
          {{w.wkdw}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          起始时间：
        </a-col>
        <a-col flex="40">
          {{setDateFormat(w.srtdat)}}
        </a-col>
        <a-col flex="10">
          终止时间：
        </a-col>
        <a-col flex="40">
          {{setDateFormat(w.enddat)}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="10">
          工作职务：
        </a-col>
        <a-col flex="40">
          {{w.wkzw}}
        </a-col>
        <a-col flex="10">
          导师信息：
        </a-col>
        <a-col flex="40">
          {{w.dsxx}}
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
        <b><font style="font-size:16px">文章信息</font></b>
      </a-col>
    </a-row>
    <a-row class="mxrowcol" v-for="y in staffInfo.essays" :key="y.id">
      <a-row type="flex" class="contentrowtop">
        <a-col flex="15">
          文章名称：
        </a-col>
        <a-col flex="90">
          {{y.lwlzmc}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15">
          作者名称：
        </a-col>
        <a-col flex="40">
          {{y.zzname}}
        </a-col>
        <a-col flex="10">
          发表期刊：
        </a-col>
        <a-col flex="40">
          {{y.fbqk}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15">
          发表或出版年度：
        </a-col>
        <a-col flex="40">
          {{y.fbcbny}}
        </a-col>
        <a-col flex="10">
          收录情况：
        </a-col>
        <a-col flex="40">
          {{y.slqk}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15">
          影响因子：
        </a-col>
        <a-col flex="40">
          {{y.yxyz}}
        </a-col>
        <a-col flex="10">
          JCR分区：
        </a-col>
        <a-col flex="40">
          {{y.jcrfq}}
        </a-col>
      </a-row>
      <a-row type="flex" class="contentrow">
        <a-col flex="15">
          他引次数：
        </a-col>
        <a-col flex="40">
          {{y.tycs}}
        </a-col>
        <a-col flex="10">
          附件材料：
        </a-col>
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
        <b><font style="font-size:16px">在校获奖情况(院级以上)</font></b>
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
        <b><font style="font-size:16px">其他</font></b>
      </a-col>
    </a-row>
    <a-row v-if="staffInfo.comments" class="mxrowcol">
      <a-col :span="24">
        {{ staffInfo.comments }}
      </a-col>
    </a-row>
    <a-affix :offsetBottom="0">
      <div class="formbutton">
      <a-button @click="onClose" type="primary" style="margin-right: .8rem">返回</a-button>
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
      dataFormat: 'YYYY-MM-DD',
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
    setFieldValues (staffId) {
      var today = new Date()
      this.$get('zpStaffInfo/staffInfoView', { staffId: staffId }).then((r) => {
        if (r.data.data.success === 1) {
          this.staffInfo = r.data.data.data
          this.baseTime = today.getTime()
        } else {
          this.$message.error('获取个人简历失败.')
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.formsty {
  width: 100%;
  padding: 12px 50px;
  background: #fff;
}
.formtab {
  width: 100%;
  padding: 8px;
}
.formtab {
  tr td {
    padding: 10px;
    border: 1px solid #ccc;
  }
  .td13 {
    width: 15%;
  }
  .td2 {
    width: 25%;
  }
  .td4 {
    width: 30%;
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
  background: #00BFFF;
}
.titlerowcol2 {
  padding-left:5px;
}
.contentrowtop{
  padding-left:20px
}
.contentrow {
  padding-top:10px;
  padding-left:20px
}
.formbutton {
  width:100%;
  padding: 20px 0px;
  background:#FFF;
  text-align: center;
  border-top:1px solid #67c799;
  button {
    width: 25%;
  }
}
</style>
