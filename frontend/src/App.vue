<template>
  <v-app>
    <v-main>
      <v-container>
        <v-card>
          <v-card-title>
            Team Calendar
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="dialog=true">Add Event</v-btn>
          </v-card-title>
          <v-card-text>
            <v-calendar
              height="600"
              :events="calendarEvents"
              type="month"
            />

            <v-dialog v-model="dialog" max-width="500px">
              <v-card>
                <v-card-title>Add Event</v-card-title>
                <v-card-text>
                  <v-text-field label="Title" v-model="form.title"></v-text-field>
                  <v-text-field label="Start" v-model="form.startTime" type="datetime-local"></v-text-field>
                  <v-text-field label="End" v-model="form.endTime" type="datetime-local"></v-text-field>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="dialog=false">Cancel</v-btn>
                  <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card-text>
        </v-card>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const events = ref([]);
const dialog = ref(false);
const form = ref({ title: '', startTime: '', endTime: '' });
const calendarEvents = ref([]);

async function load() {
  const res = await axios.get('http://localhost:8080/api/events');
  events.value = res.data;
  calendarEvents.value = events.value.map(e => ({
    name: e.title,
    start: e.startTime,
    end: e.endTime,
    color: 'primary'
  }));
}

async function save() {
  await axios.post('http://localhost:8080/api/events', form.value);
  dialog.value = false;
  form.value = { title: '', startTime: '', endTime: '' };
  load();
}

onMounted(load);
</script>
