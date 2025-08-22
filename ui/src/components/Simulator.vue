<script>
import axios from 'axios'
import Cell from './Cell.vue'

const httpClient = axios.create({
  baseURL: 'http://localhost:8080/',
  headers: {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json;charset=utf-8",
    "Access-Control-Allow-Origin": "*",
  },
});

export default {
  components: { Cell },
  data() {
    return {
      h: 0,
      w: 0,
      items: [],
      selectedCells: [],
      newWidth: 0,
      newHeight: 0
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
      this.resetBoard();

      const list = this.getCells();

      this.selectedCells = [];
    },
    callNextStep() {
      console.log("call NextStep");
      this.nextStep();
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
      const response = await httpClient.get('/api/cells');
      console.log(response);
      this.items = response.items;
    },
    async addFires() {
      const params = {
        "dryRun": false,
        "targets": this.selectedCells
      }
      const response = await httpClient.post('/api/fires', params);
      console.log(response);
    },
    async resetBoard() {
      const params = {
        "attributes": {
          "width": this.newWidth,
          "height": this.newHeight
        }
      };
      const response = await httpClient.post('/api/board', params);
      console.log(response);
    },
    async nextStep() {
      const params = {
        "dryRun": true
      };
      const response = await httpClient.post('/api/fires/next', params);
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
