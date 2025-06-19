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
                  <v-date-input
                    label="Start"
                    v-model="form.startTime"
                    type="datetime"
                  ></v-date-input>
                  <v-date-input
                    label="End"
                    v-model="form.endTime"
                    type="datetime"
                  ></v-date-input>
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
  calendarEvents.value = [];
  events.value.forEach(e => {
    const start = new Date(e.startTime);
    const end = new Date(e.endTime);
    const day = new Date(start);
    day.setHours(0,0,0,0);
    const endDay = new Date(end);
    endDay.setHours(0,0,0,0);
    for (let d = new Date(day); d <= endDay; d.setDate(d.getDate() + 1)) {
      calendarEvents.value.push({
        title: e.title,
        start: new Date(d),
        end: new Date(d),
        color: 'blue',
        allDay: true
      });
    }
  });
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
