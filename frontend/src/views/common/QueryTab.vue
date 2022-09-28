<template>
  <div>
    <a-space :size="size">
      <a-button class="editable-add-btn" type="primary" :size="size" @click="query"> 查询 </a-button>
      <a-button class="editable-add-btn" type="primary" :size="size" @click="add"> 添加 </a-button>
      <a-button class="editable-add-btn" type="primary" :size="size" @click="clear"> 清空 </a-button>
      <a-button class="editable-add-btn" :size="size" @click="close"> 关闭 </a-button>
    </a-space>
    <a-table bordered :size="size" :pagination="false" :data-source="dataSource" :columns="columns">
      <template
        slot="field"
        slot-scope="text, record, index"
      >
        <a-select
          style="width: 125px"
          :size="size"
          @change="(e) => handleChange(e, record.key, 'field')"
          v-model="record.field"
        >
          <a-select-option v-for="xx in fieldList" :key="xx.code">
            {{ xx.name }}
          </a-select-option>
        </a-select>
      </template>
      <template
        slot="yuns"
        slot-scope="text, record, index"
      >
        <a-select
          style="width: 75px"
          :size="size"
          @change="(e) => handleChange(e, record.key, 'yuns')"
          v-model="record.yuns"
        >
          <a-select-option v-for="xx in yunsList" :key="xx.code">
            {{ xx.name }}
          </a-select-option>
        </a-select>
      </template>
      <template
        slot="zhi"
        slot-scope="text, record, index"
      >
        <a-input
          style="margin: -5px 0"
          :size="size"
          :maxLength="30"
          :value="text"
          @change="(e) => handleChange(e.target.value, record.key, 'zhi')"
        />
      </template>
      <template slot="operation" slot-scope="text, record">
        <a-icon type="close-circle" v-if="record.del === 1" @click="onDelete(record.key)" />
      </template>
    </a-table>
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      size: 'small',
      dataSource: [
        {
          key: '0',
          field: '',
          yuns: 'dd',
          zhi: '',
          del: 0
        },
        {
          key: '1',
          field: '',
          yuns: 'dd',
          zhi: '',
          del: 0
        }
      ],
      // 姓名 性别 出生年月 身高 体重 视力 高考成绩 手机号码 最高学历 第一志愿
      fieldList: [
        {code: '', name: '选择'},
        {code: 'ryname', name: '姓名'},
        {code: 'sex', name: '性别'},
        {code: 'csdat', name: '出生日期'},
        {code: 'idnumber', name: '身份证号'},
        {code: 'tel', name: '手机号码'},
        {code: 'zhrsg', name: '身高cm'},
        {code: 'zhrtz', name: '体重kg'},
        {code: 'wyspfs', name: '外语分数'},
        {code: 'zgxl', name: '最高学历'},
        {code: 'zyks1', name: '第一志愿科室'}
      ],
      yunsList: [
        {code: 'dd', name: '='},
        {code: 'dy', name: '>'},
        {code: 'xy', name: '<'},
        {code: 'ddd', name: '>='},
        {code: 'xdd', name: '<='},
        {code: 'like', name: '包含'}
      ],
      count: 2,
      columns: [
        {
          title: '字段选项',
          dataIndex: 'field',
          scopedSlots: { customRender: 'field' }
        },
        {
          title: '运算符',
          dataIndex: 'yuns',
          scopedSlots: { customRender: 'yuns' }
        },
        {
          title: '值',
          dataIndex: 'zhi',
          scopedSlots: { customRender: 'zhi' }
        },
        {
          title: '操作',
          dataIndex: 'operation',
          scopedSlots: { customRender: 'operation' }
        }
      ]
    }
  },
  methods: {
    handleChange (value, key, column) {
      const newData = [...this.dataSource]
      const target = newData.filter(item => key === item.key)[0]
      if (target) {
        target[column] = value
        this.dataSource = newData
      }
    },
    onDelete (key) {
      const dataSource = [...this.dataSource]
      this.dataSource = dataSource.filter(item => item.key !== key)
    },
    query () {
      let data = []
      this.dataSource.forEach((item, index, arr) => {
        if (item.field && item.zhi) {
          data.push({f: item.field, y: item.yuns, z: item.zhi})
        }
      })
      this.$emit('qtquery', data)
    },
    clear () {
      this.count = 2
      this.dataSource = [
        {
          key: '0',
          field: '',
          yuns: 'dd',
          zhi: '',
          del: 0
        },
        {
          key: '1',
          field: '',
          yuns: 'dd',
          zhi: '',
          del: 0
        }
      ]
    },
    close () {
      this.$emit('close')
    },
    add () {
      if (this.dataSource.length < 5) {
        const { count, dataSource } = this
        const newData = {
          key: count,
          field: '',
          yuns: 'dd',
          zhi: '',
          del: 1
        }
        this.dataSource = [...dataSource, newData]
        this.count = count + 1
      }
    }
  }
}
</script>
<style>
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
