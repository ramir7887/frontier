<template>
    <div class="home">
        <div class="heading">
            <h1 class="title">Научные области</h1>

            <div class="buttons">
                <v-btn outlined color="primary" class="mr-2" @click="$refs.addSubjectModal.open()">
                    <v-icon left> add </v-icon>
                    Добавить технологию
                </v-btn>

                <v-btn
                    color="primary"
                    :disabled="!selectedItemsAmount"
                    depressed
                    @click="goToAnalyzePage"
                >
                    <v-icon left> analytics </v-icon>
                    Проанализировать
                </v-btn>
            </div>
        </div>

        <section class="science-branches">
            <ScienceBranchCard
                v-for="s in scienceBranches"
                :key="s.branchScience.id"
                :name="s.branchScience.name"
                :frontiers-amount="s.frontirCount"
                :subjects-amount="s.subjectCount"
                :selected="s.selected"
                :icons="s.icons"
                @toggleSelect="s.selected = !s.selected"
            />
        </section>

        <AddSubjectModal ref="addSubjectModal" />
    </div>
</template>

<script>
import ScienceBranchCard from "../components/ScienceBranchCard";
import { httpClient } from "../http-client";
import AddSubjectModal from "../components/AddSubjectModal";
import {mapState} from "vuex";

export default {
    name: "Home",
    components: { AddSubjectModal, ScienceBranchCard },
    data: () => ({
        scienceBranches: []
    }),
    async mounted() {
        const branches = (await httpClient().get("/branch")).data;
        const icons = {
            2: "mathematics",
            1: "mechanical",
            3: "computer",
            4: "physic",
            5: "chemical",
            6: "biological",
            7: "health",
            8: "medicine",
            9: "earth",
            10: "social",
            11: "humanities",
        }

        this.scienceBranches = branches.map(b => ({
            ...b,
            selected: false,
            icons: require("../assets/img/" + icons[b.branchScience.id]+".svg")
        }));
    },
	watch: {
		dataSourceName() {
			this.loadData();
		}
	},
    computed: {
    	...mapState(["dataSourceName"]),
        selectedItems() {
            return this.scienceBranches.filter(b => b.selected);
        },
        selectedItemsAmount() {
            return this.selectedItems.length;
        }
    },
    methods: {
    	loadData() {
    		if (this.dataSourceName === "Области") {

			} else if (this.dataSourceName === "Источники") {

			}
		},
        goToAnalyzePage() {
            const selectedIds = this.selectedItems.map(i => i.branchScience.id);
            this.$router.push(`/analyze?branches=${selectedIds.join(",")}`);
        }
    }
};
</script>

<style scoped lang="scss">
.title{
    color: #2b2b2b
}

.heading {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 20px;
}

.science-branches {
    margin-top: 40px;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    column-gap: 18px;
    row-gap: 23px;
}
</style>
