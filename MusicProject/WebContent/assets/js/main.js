let shownItems = 25,
	artists = document.getElementById("artists"),
	rows = artists.getElementsByTagName("tr").length,
	loadMore = document.getElementById("loadMore");

loadMore.addEventListener("click", () => {
	if(shownItems >= rows) {
		loadMore.innerText = "No more results";
		return;
	}
	shownItems = shownItems + 25;
	for(let [i,row] of [...artists.rows].entries()) {
		if(shownItems >= i) {
			row.classList.remove("hidden");
		}
	}
});