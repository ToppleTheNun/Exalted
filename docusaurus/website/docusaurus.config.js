const versions = require("./versions.json");

const isDev = process.env.NODE_ENV === "development";
const isVersioningDisabled = !!process.env.DISABLE_VERSIONING;

module.exports = {
    title: "Exalted",
    tagline: "Next generation of ARPG drops for Minecraft",
    url: "https://topplethenun.github.io",
    baseUrl: "/exalted",
    baseUrlIssueBanner: true,
    favicon: "img/favicon.ico",
    organizationName: "ToppleTheNun",
    projectName: "exalted",
    onBrokenLinks: "throw",
    onBrokenMarkdownLinks: "warn",
    presets: [
        [
            "@docusaurus/preset-classic",
            {
                docs: {
                    path: "../docs",
                    editUrl:
                        "https://github.com/ToppleTheNun/exalted/edit/main/website/",
                    sidebarPath: require.resolve("./sidebars.js"),
                    showLastUpdateTime: true,
                    disableVersioning: isVersioningDisabled,
                    lastVersion: isDev ? "current" : undefined,
                    onlyIncludeVersions:
                        !isVersioningDisabled && isDev
                            ? ["current", ...versions.slice(0, 2)]
                            : undefined,
                    versions: {
                        current: {
                            label: `Current 🚧`,
                        },
                    },
                },
                theme: {
                    customCss: require.resolve("./src/css/custom.css"),
                },
            },
        ],
    ],
    themeConfig: {
        hideableSidebar: true,
        colorMode: {
            defaultMode: "light",
            disableSwitch: false,
            respectPrefersColorScheme: true,
        },
        prism: {
            theme: require("prism-react-renderer/themes/github"),
            darkTheme: require("prism-react-renderer/themes/dracula"),
        },
        navbar: {
            hideOnScroll: true,
            title: "Exalted",
            logo: {
                alt: "Exalted Logo",
                src: "img/logo.svg",
            },
            items: [
                {
                    type: "doc",
                    position: "left",
                    docId: "installation",
                    label: "Docs",
                },
                {
                    type: "docsVersionDropdown",
                    position: "right",
                    dropdownActiveClassDisabled: true,
                    dropdownItemsAfter: [
                        {
                            to: "/versions",
                            label: "All versions",
                        },
                    ],
                },
                {
                    href: "https://github.com/ToppleTheNun/exalted",
                    position: "right",
                    className: "header-github-link",
                    "aria-label": "GitHub repository",
                },
            ],
        },
        footer: {
            style: "dark",
            copyright: `Copyright © ${new Date().getFullYear()} Richard Harrah. Built with Docusaurus.`,
            links: [],
        },
    },
};
