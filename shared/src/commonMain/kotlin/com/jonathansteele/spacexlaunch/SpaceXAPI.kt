package com.jonathansteele.spacexlaunch

import com.github.kittinunf.result.onFailure
import com.github.kittinunf.result.onSuccess
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import fuel.Fuel
import fuel.post
import fuel.serialization.toJson
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

object SpaceXAPI {
    private const val launches = """{
	"options": {
		"pagination": false,
		"select": {
			"links.patch.large": 0,
			"links.reddit": 0,
			"links.flickr": 0,
			"links.youtube_id": 0,
			"links.article": 0,
			"links.wikipedia": 0,
            "launchpad.images": 0,
			"fairings.ships": 0,
			"tbd": 0,
			"net": 0,
			"static_fire_date_unix": 0,
			"auto_update": 0,
			"date_unix": 0,
			"date_local": 0,
			"ships": 0,
			"capsules": 0,
			"crew": 0
		},
		"populate": [{
				"path": "rocket",
				"select": {
					"name": 1
				}
			},
			{
				"path": "launchpad",
				"select": {
					"rockets": 0,
					"launches": 0,
					"timezone": 0
				}
			},
			{
				"path": "payloads",
				"select": {
					"type": 0,
					"launch": 0,
					"norad_ids": 0,
					"mass_lbs": 0,
					"longitude": 0,
					"semi_major_axis_km": 0,
					"eccentricity": 0,
					"lifespan_years": 0,
					"epoch": 0,
					"regime": 0,
					"mean_motion": 0,
					"raan": 0,
					"arg_of_pericenter": 0,
					"mean_anomaly": 0,
					"reference_system": 0,
					"dragon.mass_returned_lbs": 0,
					"dragon.mass_returned_kg": 0,
					"dragon.flight_time_sec": 0,
					"dragon.manifest": 0,
					"dragon.water_landing": 0,
					"dragon.land_landing": 0
				},
				"populate": {
					"path": "dragon.capsule",
					"select": {
						"mass_returned_lbs": 0,
						"land_landings": 0
					},
					"populate": {
						"path": "launches",
						"select": {
							"name": 1,
							"flight_number": 1,
							"date_utc": 1
						}
					}
				}
			},
			{
				"path": "cores",
				"select": {
					"flight": 0
				},
				"populate": [{
						"path": "core",
						"populate": {
							"path": "launches",
							"select": {
								"name": 1,
								"flight_number": 1,
								"date_utc": 1
							}
						}
					},
					{
						"path": "landpad",
						"select": {
							"launches": 0
						}
					}
				]
			}
		]
	}
}"""
    @NativeCoroutines
    fun fetchAllLaunches() = flow {
        val json = Json { ignoreUnknownKeys = true }
        Fuel.post(
            url = "https://api.spacexdata.com/v5/launches/query",
            body = launches,
            headers = mapOf("Content-Type" to "application/json; charset=utf-8")
        ).toJson(
            json = json,
            deserializationStrategy = Launch.serializer()
        ).onSuccess {
            emit(it?.docs)
        }.onFailure {
            log("SpaceXAPI", "Couldn't get Launches", it)
        }
    }
}
