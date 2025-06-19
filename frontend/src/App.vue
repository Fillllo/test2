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
    title: e.title,
    start: new Date(e.startTime),
    end: new Date(e.endTime),
    color: 'primary'
  }));
}

async function save() {
  const payload = {
    title: form.value.title,
    startTime: form.value.startTime
      ? new Date(form.value.startTime).toISOString().slice(0, 19)
      : null,
    endTime: form.value.endTime
      ? new Date(form.value.endTime).toISOString().slice(0, 19)
      : null,
  };
  await axios.post('http://localhost:8080/api/events', payload);
  dialog.value = false;
  form.value = { title: '', startTime: '', endTime: '' };
  load();
}

onMounted(load);
</script>
