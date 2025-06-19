import { createApp } from 'vue';
import App from './App.vue';
import { createVuetify } from 'vuetify';
import 'vuetify/styles';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import { VCalendar } from 'vuetify/labs/VCalendar';
import { VDateInput } from 'vuetify/labs/VDateInput';

const vuetify = createVuetify({
  components: {
    ...components,
    VCalendar,
    VDateInput,
  },
  directives,
});

createApp(App).use(vuetify).mount('#app');
