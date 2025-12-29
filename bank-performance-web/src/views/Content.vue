<template>
    <div>
        <template v-if="user">
            <Header />
            <div class="container">
                <aside>
                    <Menu />
                </aside>
                <main>
                    <router-view />
                </main>
            </div>
            <Footer />
        </template>
    </div>
</template>

<script>
    import { mapActions, mapState } from "vuex"
    import Cookies from "js-cookie"
    export default {
        name: "Content",
        computed: {
            ...mapState("user", ["user"])
        },
        components: {
            Header: () => import("../components/share/Header"),
            Footer: () => import("../components/share/Footer"),
            Menu: () => import("../components/share/Menu")
        },
        mounted() {
            this.getInfo()
        },
        methods: {
            ...mapActions("user", ["AGet"]),
            async getInfo() {
                const loading = this.$loading();
                await this.AGet();
                loading.close();
                if (!this.user) {
                    Cookies.remove("token");
                    await this.$router.replace("/login");
                }
            }
        }
    }
</script>

<style type="text/less" lang="less" scoped></style>