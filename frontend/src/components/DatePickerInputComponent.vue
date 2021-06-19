<template>
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="auto"
    >
        <template v-slot:activator="{ on, attrs }">
            <v-text-field
                :value="formattedDateString"
                :label="placeholder"
                hide-details
                prepend-icon="event"
                readonly
                v-bind="attrs"
                v-on="on"
            />
        </template>
        <v-date-picker :value="datepickerDateString" @input="dateInputHandler" />
    </v-menu>
</template>

<script>
import dayjs from "dayjs";

export default {
    name: "DatePickerInputComponent",
    props: {
        value: {
            type: Date,
            required: false,
            default: null
        },
        placeholder: {
            type: String,
            required: false,
            default: ""
        }
    },
    computed: {
        datepickerDateString() {
            if (this.value) {
                return dayjs(this.value).format("YYYY-MM-DD");
            }

            return "";
        },
        formattedDateString() {
            if (this.value) {
                return dayjs(this.value).format("DD.MM.YYYY");
            }

            return "";
        }
    },
    data: () => ({
        menu: false
    }),
    methods: {
        dateInputHandler(date) {
            const parsed = dayjs(date);
            this.$emit("input", parsed.toDate());
            this.menu = false;
        }
    }
};
</script>

<style scoped></style>
