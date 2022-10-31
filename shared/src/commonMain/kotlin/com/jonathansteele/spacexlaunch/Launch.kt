package com.jonathansteele.spacexlaunch

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    val docs: List<Doc>,
    val hasNextPage: Boolean, // false
    val hasPrevPage: Boolean, // false
    val limit: Int, // 205
    val offset: Int, // 0
    val page: Int, // 1
    val pagingCounter: Int, // 1
    val totalDocs: Int, // 205
    val totalPages: Int // 1
) {
    @Serializable
    data class Doc(
        val cores: List<Core>,
        @SerialName("date_precision")
        val datePrecision: String, // hour
        @SerialName("date_utc")
        val dateUtc: Instant, // 2006-03-24T22:30:00.000Z
        val details: String?, // Engine failure at 33 seconds and loss of vehicle
        val failures: List<Failure>,
        val fairings: Fairings?,
        @SerialName("flight_number")
        val flightNumber: Int, // 1
        val id: String, // 5eb87cd9ffd86e000604b32a
        @SerialName("launch_library_id")
        val launchLibraryId: String?, // 110c808a-a091-47ab-8532-4fa058c1de7a
        val launchpad: Launchpad,
        val links: Links,
        val name: String, // FalconSat
        val payloads: List<Payload>,
        val rocket: Rocket,
        @SerialName("static_fire_date_utc")
        val staticFireDateUtc: Instant?, // 2006-03-17T00:00:00.000Z
        val success: Boolean?, // false
        val upcoming: Boolean, // false
        val window: Int? // 0
    ) {
        @Serializable
        data class Core(
            val core: Core?,
            val flight: Int?, // 1
            val gridfins: Boolean?, // false
            @SerialName("landing_attempt")
            val landingAttempt: Boolean?, // false
            @SerialName("landing_success")
            val landingSuccess: Boolean?, // false
            @SerialName("landing_type")
            val landingType: String?, // Ocean
            val landpad: Landpad?,
            val legs: Boolean?, // false
            val reused: Boolean? // false
        ) {
            @Serializable
            data class Core(
                @SerialName("asds_attempts")
                val asdsAttempts: Int, // 0
                @SerialName("asds_landings")
                val asdsLandings: Int, // 0
                val block: Int?, // 1
                val id: String, // 5e9e289df35918033d3b2623
                @SerialName("last_update")
                val lastUpdate: String?, // Engine failure at T+33 seconds resulted in loss of vehicle
                @SerialName("reuse_count")
                val reuseCount: Int, // 0
                @SerialName("rtls_attempts")
                val rtlsAttempts: Int, // 0
                @SerialName("rtls_landings")
                val rtlsLandings: Int, // 0
                val serial: String, // Merlin1A
                val status: String // lost
            )

            @Serializable
            data class Landpad(
                val details: String, // The ASDS landing location for the first landing test was in the Atlantic approximately 200 miles (320 km) northeast of the launch location at Cape Canaveral, and 165 miles (266 km) southeast of Charleston, South Carolina. SpaceX's Just Read the Instructions, based on the Marmac 300 deck barge, in position for a landing test on Falcon 9 Flight 17 in April 2015. On 23 January 2015, during repairs to the ship following the unsuccessful first test, Musk announced that the ship was to be named Just Read the Instructions, with a sister ship planned for west coast launches to be named Of Course I Still Love You. On 29 January, SpaceX released a manipulated photo of the ship with the name illustrating how it would look once painted. Both ships are named after two General Contact Units, spaceships commanded by autonomous artificial intelligences, that appear in The Player of Games, a Culture novel by Iain M. Banks. The first Just Read the Instructions was retired in May 2015 after approximately six months of service in the Atlantic, and its duties were assumed by Of Course I Still Love You. The former ASDS was modified by removing the wing extensions that had extended the barge surface and the equipment (thrusters, cameras and communications gear) that had been added to refit it as an ASDS; these items were saved for future reuse.
                @SerialName("full_name")
                val fullName: String, // Just Read The Instructions V1
                val id: String, // 5e9e3032383ecb761634e7cb
                val images: Images,
                @SerialName("landing_attempts")
                val landingAttempts: Int, // 2
                @SerialName("landing_successes")
                val landingSuccesses: Int, // 0
                val latitude: Double, // 28.4104
                val locality: String, // Port Canaveral
                val longitude: Double, // -80.6188
                val name: String, // JRTI-1
                val region: String, // Florida
                val status: String, // retired
                val type: String, // ASDS
                val wikipedia: String // https://en.wikipedia.org/wiki/Autonomous_spaceport_drone_ship
            ) {
                @Serializable
                data class Images(
                    val large: List<String>
                )
            }
        }

        @Serializable
        data class Failure(
            val altitude: Int?, // 289
            val reason: String, // merlin engine failure
            val time: Int // 33
        )

        @Serializable
        data class Fairings(
            val recovered: Boolean?, // false
            @SerialName("recovery_attempt")
            val recoveryAttempt: Boolean?, // false
            val reused: Boolean? // false
        )

        @Serializable
        data class Launchpad(
            val details: String, // SpaceX's original pad, where all of the Falcon 1 flights occurred (from 2006 to 2009). It would have also been the launch site of the Falcon 1e and the Falcon 9, but it was abandoned as SpaceX ended the Falcon 1 program and decided against upgrading it to support Falcon 9, likely due to its remote location and ensuing logistics complexities.
            @SerialName("full_name")
            val fullName: String, // Kwajalein Atoll Omelek Island
            val id: String, // 5e9e4502f5090995de566f86
            val latitude: Double, // 9.0477206
            @SerialName("launch_attempts")
            val launchAttempts: Int, // 5
            @SerialName("launch_successes")
            val launchSuccesses: Int, // 2
            val locality: String, // Omelek Island
            val longitude: Double, // 167.7431292
            val name: String, // Kwajalein Atoll
            val region: String, // Marshall Islands
            val status: String // retired
        )

        @Serializable
        data class Links(
            val patch: Patch,
            val presskit: String?, // http://www.spacex.com/press/2012/12/19/spacexs-falcon-1-successfully-delivers-razaksat-satellite-orbit
            val webcast: String? // https://www.youtube.com/watch?v=0a_00nJ_Y88
        ) {
            @Serializable
            data class Patch(
                val small: String? // https://images2.imgbox.com/94/f2/NN6Ph45r_o.png
            )
        }

        @Serializable
        data class Payload(
            @SerialName("apoapsis_km")
            val apoapsisKm: Double?, // 619.727
            val customers: List<String>,
            val dragon: Dragon,
            val id: String, // 5eb0e4b5b6c3bb0006eeb1e1
            @SerialName("inclination_deg")
            val inclinationDeg: Double?, // 9.3452
            val manufacturers: List<String>,
            @SerialName("mass_kg")
            val massKg: Double?, // 5383.85
            val nationalities: List<String>,
            val orbit: String?, // LEO
            @SerialName("periapsis_km")
            val periapsisKm: Double?, // 601.983
            @SerialName("period_min")
            val periodMin: Double?, // 96.913
            val reused: Boolean // false
        ) {
            @Serializable
            data class Dragon(
                val capsule: Capsule?
            ) {
                @Serializable
                data class Capsule(
                    val id: String, // 5e9e2c5bf35918ed873b2664
                    @SerialName("last_update")
                    val lastUpdate: String?, // Hanging in atrium at SpaceX HQ in Hawthorne 
                    val launches: List<Launche>,
                    @SerialName("reuse_count")
                    val reuseCount: Int, // 0
                    val serial: String, // C101
                    val status: String, // retired
                    val type: String, // Dragon 1.0
                    @SerialName("water_landings")
                    val waterLandings: Int // 1
                ) {
                    @Serializable
                    data class Launche(
                        @SerialName("date_utc")
                        val dateUtc: String, // 2010-12-08T15:43:00.000Z
                        @SerialName("flight_number")
                        val flightNumber: Int, // 7
                        val id: String, // 5eb87cdeffd86e000604b330
                        val name: String // COTS 1
                    )
                }
            }
        }

        @Serializable
        data class Rocket(
            val id: String, // 5e9d0d95eda69955f709d1eb
            val name: String // Falcon 1
        )
    }
}