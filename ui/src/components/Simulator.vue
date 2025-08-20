<script>
import axios from 'axios'
import Cell from './Cell.vue'

export default {
  components: { Cell },
  data() {
    return {
      h: 2,
      w: 4,
      items: [],
      selectedCells: [],
      newWidth: 4,
      newHeight: 2
    };
  },
  computed: {
    // a computed getter
    myComputedContent() {

      console.log("computed()");
      return this.items;
    },
  },
  mounted() {

    console.log("mounted()");
    if (this.w === this.newWidth && this.h === this.newHeight) {
      return;
    }

    console.log("mounted() updates items");
    let array = [];
    for(var i=0;i<this.newHeight;i++) {
        array[i] = [];
        for(var j=0; j<this.newWidth; j++) {
            array[i][j]={
              x: j,
              y: i,
              value: "X",
              isAlive: true,
              isBurning: false,
              isDead: false
            };
        }
    }
    this.items = array;
    this.w = this.newWidth;
    this.h = this.newHeight;

    this.selectedCells = [];
  },
  emits: ['update:h', 'update:w'],
  methods: {
    callReset() {
      console.log("call Reset : " + this.newWidth + ", " + this.newHeight);
      this.$forceUpdate();
    },
    callNextStep() {
      console.log("call NextStep");
    },
    callNextWidth(value) {
      console.log("call NextWidth : " + value);
      this.newWidth = value;
    },
    callNextHeight(value) {
      console.log("call NextHeight : " + value);
      this.newHeight = value;
    },
    handleMessage(message) {
      if (this.selectedCells.includes(message)) {
        console.log("do not handleMessage : ", message);
        return;
      }
      console.log("handleMessage : ", message);
      this.selectedCells.push(message);
    },
    async getCells() {
      const response = await axios.get('/api/cells');
      console.log(response);
      this.items = response.items;
    },
    async addFires() {
      const response = await axios.post('/api/fires')
      console.log(response);
    },
    async resetBoard() {
      const response = await axios.post('/api/board')
      console.log(response);
    },
    async nextStep() {
      const response = await axios.post('/api/fires/next')
      console.log(response);
    }
  }
}
</script>

<template>
    <div class="simulator">
        <h1 class="green">Simulateur de propagation d'incendies</h1>
        <table class="board">
          <tr v-for="j in h">
            <td v-for="i in w">
              <Cell v-model="items[j-1][i-1]" @clickEvent="handleMessage"/>
            </td>
          </tr>
        </table>
        <div>
          <span>W: </span><input type="text"
                                 :value="w"
                                 @input="callNextWidth($event.target.value)" />
          <span>H: </span><input type="text"
                                 :value="h"
                                 @input="callNextHeight($event.target.value)" />
          <button @click="callReset()">reset</button>
          <button @click="callNextStep()">next</button>
        </div>
    </div>
</template>

<style>
.simulator h1,
.simulator h3 {
  text-align: center;
}
</style>
