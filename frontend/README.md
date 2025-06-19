# Frontend

Vue 3 application using Vuetify to display team calendar events.
The main page shows a monthly calendar highlighting days with events.

## Running

Install dependencies and start the dev server:

```bash
npm install
npm run dev
```

Vite is configured to process `.vue` files via `@vitejs/plugin-vue` (see
`vite.config.js`).

The calendar uses Vuetify's `VCalendar` lab component which is registered
manually in `src/main.js`.

Event start and end times from the backend are converted to JavaScript `Date`
objects in `App.vue` before being passed to the calendar to avoid runtime
errors.
