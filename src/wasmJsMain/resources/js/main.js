export function initUI(sketchJson) {
    let links;
    let activeLink = null;
    const defaultNavWidth = '240px';
    let navWidth = defaultNavWidth;
    let navHidden = 'true';
    let sketches = []

    const nav = document.getElementById('sidebar');

    function setNavGroupOpenState(targetGroup) {
        let groups = nav.querySelectorAll('nav details.group')
        groups.forEach(g => {
            const shouldOpen = g === targetGroup;
            g.open = shouldOpen;
            g.setAttribute('aria-expanded', String(shouldOpen));
        });
    }

    function setActiveNavItem(activeNavId) {
        let newActiveNav = nav.querySelector(`#${activeNavId}`);
        if (!newActiveNav) return;

        let currentActiveNav = nav.querySelector('nav a.active');
        if (currentActiveNav) {
            currentActiveNav.classList.remove('active');
            currentActiveNav.setAttribute('aria-current', 'false');
        }
        newActiveNav.classList.add('active');
        newActiveNav.setAttribute('aria-current', 'page');
        const group = newActiveNav.closest('nav details.group');
        if (group) setNavGroupOpenState(group);
        return newActiveNav
    }


    function stepActiveNavItem(delta) {
        if (!links.length) return;
        const idx = activeLink ? links.indexOf(activeLink) : -1;
        const targetIdx = (idx + delta) < 0 ? links.length - 1 : (idx + delta) % links.length;
        const nextActiveNavId = links[targetIdx].id;
        if (nextActiveNavId) activeLink = setActiveNavItem(nextActiveNavId);
        activeLink.click()
    }

    function initNavLinks(sketchData) {
        const nav = document.createElement('div');
        nav.className = 'groups';
        nav.setAttribute('role', 'tree');

        let sketchIndex = 0
        Object.keys(sketchData).forEach(groupName => {
            let groupData = sketchData[groupName];
            const details = document.createElement('details');
            details.className = 'group';
            details.setAttribute('role', 'treeitem');
            details.open = false;
            details.setAttribute('aria-expanded', 'false');

            const summary = document.createElement('summary');
            summary.textContent = groupName;
            details.appendChild(summary);

            const ul = document.createElement('ul');
            ul.id = groupName;
            ul.setAttribute('role', 'group');

            groupData.forEach((sketch) => {
                const li = document.createElement('li');
                const link = document.createElement('a');
                link.href = '#';
                link.id = sketch['funcId'];
                link.textContent = sketch['navTitle'];
                link.dataset.sketchId = String(sketchIndex++);
                link.className = 'nav-link';
                sketches.push(sketch);

                li.appendChild(link);
                ul.appendChild(li);
            });

            details.appendChild(ul);
            nav.appendChild(details);

            nav.addEventListener('click', (event) => {
                const clickedLink = event.target.closest('a.nav-link')
                if (clickedLink) {
                    const sketch = sketches[Number(clickedLink.dataset.sketchId)]
                    sessionStorage.setItem('funcId', sketch['funcId']);
                    sessionStorage.setItem('navWidth', navWidth);
                    sessionStorage.setItem('navHidden', navHidden);
                    sessionStorage.setItem('codeLink', sketch['codeLink']);
                    sessionStorage.setItem('docLink', sketch['docLink']);
                    sessionStorage.setItem('title', sketch['title']);
                    sessionStorage.setItem('comment', sketch['comment']);
                    document.location.reload();
                }
            })
        });

        return nav;
    }

    function initNavStatus() {
        const newNavWidth = sessionStorage.getItem('navWidth') ?? defaultNavWidth;
        const newNavHidden = sessionStorage.getItem('navHidden') ?? 'false';

        if (newNavWidth !== navWidth) {
            navWidth = newNavWidth;
            sessionStorage.setItem('navWidth', navWidth);
        }

        if (newNavHidden !== navHidden) {
            navHidden = newNavHidden;
            sessionStorage.setItem('navHidden', navHidden);
        }
        nav.style.width = navWidth;
        nav.setAttribute('aria-hidden', navHidden);

        if (navHidden === 'true') {
            document.body.classList.add('nav-collapsed');
            nav.classList.remove('visible');
        } else {
            document.body.classList.remove('nav-collapsed');
            nav.classList.add('visible');
        }
    }

    function init() {
        const sketchData = JSON.parse(sketchJson)

        nav.appendChild(initNavLinks(sketchData))

        initNavStatus()

        links = Array.from(nav.querySelectorAll('a[href]'));

        let btnPrev = document.getElementById('navPrev');
        let btnNext = document.getElementById('navNext');
        let btnToggle = document.getElementById('toggleSidebar');

        let activeNavId = sessionStorage.getItem('funcId');

        if (activeNavId) activeLink = setActiveNavItem(activeNavId);


        // Header nav buttons
        btnPrev?.addEventListener('click', () => stepActiveNavItem(-1));
        btnNext?.addEventListener('click', () => stepActiveNavItem(1));

        // Sidebar toggle
        btnToggle?.addEventListener('click', () => {
            navHidden = navHidden === 'true' ? 'false' : 'true';
            if (navHidden === 'true') {
                document.body.classList.add('nav-collapsed');
                nav.classList.remove('visible');
            } else {
                document.body.classList.remove('nav-collapsed');
                nav.classList.add('visible');
            }
            nav.setAttribute('aria-hidden',navHidden);
            btnToggle.title = Boolean(navHidden) ? 'Expand sidebar' : 'Collapse sidebar';
            btnToggle.setAttribute('aria-label', btnToggle.title);
            sessionStorage.setItem('navHidden', navHidden);
        });

        const resizeObserver = new ResizeObserver(entries => {
            for (let entry of entries) {
                navWidth = entry.target.style.width;
                sessionStorage.setItem('navWidth', navWidth);
            }
        });

        resizeObserver.observe(nav);

        const codeLink = sessionStorage.getItem('codeLink');
        if (codeLink) {
            const codeButton = document.getElementById('code-btn');
            if (codeButton) {
                codeButton.disabled = false;
                codeButton.addEventListener('click', (event) => {
                    event.preventDefault();
                    window.open(codeLink, '_blank');
                })
            }
        }

        const docLink = sessionStorage.getItem('docLink');
        if (docLink) {
            const docButton = document.getElementById('doc-btn');
            if (docButton) {
                docButton.disabled = false;
                docButton.addEventListener('click', (event) => {
                    event.preventDefault();
                    window.open(docLink, '_blank');
                })
            }
        }

        const title = sessionStorage.getItem('title');
        if (title) {
            const titleEl = document.getElementById('title');
            if (titleEl) {
                titleEl.textContent = title;
            }
        }

        const comment = sessionStorage.getItem('comment');
        if (comment) {
            const commentEl = document.getElementById('comment');
            if (commentEl) {
                commentEl.textContent = comment;
            }
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
}