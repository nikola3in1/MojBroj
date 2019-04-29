<template>
  <v-container>
    <v-layout text-xs-center wrap>
      <v-flex xs12>
        <v-img :src="require('../assets/logo.png')" class="my-3" contain height="200"></v-img>
      </v-flex>

      <v-flex mb-4>
        <h1 class="display-2 font-weight-bold mb-3">Welcome to MojBroj</h1>
        <p class="subheading font-weight-regular">
          Web application for solving number puzzles,
          <br>Inspired by one of the games from the popular Serbian TV quiz "Slagalica".
        </p>
      </v-flex>

      <v-flex mb-1 xs12>
        <h2 class="headline font-weight-bold mb-3">Enter The Puzzle</h2>
        <v-form v-model="valid">
          <v-container>
            <v-layout justify-center>
              <v-flex xs4 sm2 md1>
                <v-text-field type="number" min="1" max="9" v-model="nr1" label="1-9" outline></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr2"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr3"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm2 md1>
                <v-text-field
                  type="number"
                  min="1"
                  max="9"
                  v-model="nr4"
                  label="1-9"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md2 lg2 xl1>
                <v-text-field
                  type="number"
                  v-model="nr5"
                  label="10, 15, 20"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md3 lg3 xl2>
                <v-text-field
                  type="number"
                  v-model="nr6"
                  label="25, 50, 75, 100"
                  required
                  outline
                ></v-text-field>
              </v-flex>
              <v-flex xs4 sm4 md2 lg2 xl1>
                <v-text-field
                  type="number"
                  v-model="target"
                  label="Target"
                  required
                  outline
                ></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
          <v-btn color="primary" large @click="submit">submit</v-btn>
          <v-btn color="error" large @click="clear">clear</v-btn>
        </v-form>
      </v-flex>

      <!-- <v-flex xs12 mb-5>
        <h2 class="headline font-weight-bold mb-3">Solutions</h2>
        <v-layout justify-center>
          <a
            v-for="(link, i) in importantLinks"
            :key="i"
            :href="link.href"
            class="subheading mx-3"
            target="_blank"
          >{{ link.text }}</a>
        </v-layout>
      </v-flex> -->
    </v-layout>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, between, numeric } from "vuelidate/lib/validators";
import { constants } from 'crypto';
export default {
  mixins: [validationMixin],

  validations: {
    nr1: { numeric, between: between(1, 9) },
    nr2: { numeric, between: between(1, 9) },
    nr3: { numeric, between: between(1, 9) },
    nr4: { numeric, between: between(1, 9) },
    nr5: { numeric, between: between(10, 20) },
    nr6: { numeric, between: between(25, 100) },
    target: { numeric, required, between: between(1, 999) }
  },

  data: () => ({
    valid: Boolean,
    nr1: Number,
    nr2: Number,
    nr3: Number,
    nr4: Number,
    nr5: Number,
    nr6: Number,
    target: Number
  }),

  methods: {
    submit:function() {
      console.log("submit");
      let numbersArr = [];
      numbersArr.push(
        this.nr1,
        this.nr2,
        this.nr3,
        this.nr4,
        this.nr5,
        this.nr6
      );
      this.target = parseInt(this.target);
      let body = {
        numbers: numbersArr,
        target: this.target
      };
      console.log(body)
      this.$http.post("http://localhost:3030/api/v1/solve",body)
      .then(function(data){
        console.log(data);
      });

    },
    clear() {
      this.nr1 = 0;
      this.nr2 = 0;
      this.nr3 = 0;
      this.nr4 = 0;
      this.nr5 = 0;
      this.nr6 = 0;
      this.target = 0;
    }
  }
};
</script>

<style>
.input {
  width: 80% !important;
}
.smallInput {
  width: 60% !important;
}
</style>
