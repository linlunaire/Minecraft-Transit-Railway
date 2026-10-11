package mtr.data

class DataConverter : NameColorDataBase {
	constructor(name: String?, color: Int) : super() {
		this.name = name
		this.color = color
	}

	constructor(id: Long, name: String?, color: Int) : super(id) {
		this.name = name
		this.color = color
	}

	override fun hasTransportMode(): Boolean = false
}
