package com.darkmoose117.scryfall.preview

import com.darkmoose117.scryfall.customizeForScryfall
import com.darkmoose117.scryfall.data.Card
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

object SpecificCards {

    private val moshi by lazy {
        Moshi.Builder().customizeForScryfall().build()
    }

    val AvacynArchangel: Card = moshi.adapter(Card::class.java).fromJson("""{
      "object": "card",
      "id": "ae155ee2-008f-4dc6-82bf-476be7baa224",
      "oracle_id": "432b37a5-d32a-4b78-91ab-860aa026b7cc",
      "multiverse_ids": [
        409741,
        409742
      ],
      "mtgo_id": 59766,
      "mtgo_foil_id": 59767,
      "tcgplayer_id": 114848,
      "cardmarket_id": 288641,
      "name": "Archangel Avacyn // Avacyn, the Purifier",
      "lang": "en",
      "released_at": "2016-04-08",
      "uri": "https://api.scryfall.com/cards/ae155ee2-008f-4dc6-82bf-476be7baa224",
      "scryfall_uri": "https://scryfall.com/card/soi/5/archangel-avacyn-avacyn-the-purifier?utm_source=api",
      "layout": "transform",
      "highres_image": true,
      "image_status": "highres_scan",
      "cmc": 5.0,
      "type_line": "Legendary Creature — Angel // Legendary Creature — Angel",
      "color_identity": [
        "R",
        "W"
      ],
      "keywords": [
        "Flying",
        "Vigilance",
        "Transform",
        "Flash"
      ],
      "card_faces": [
        {
          "object": "card_face",
          "name": "Archangel Avacyn",
          "mana_cost": "{3}{W}{W}",
          "type_line": "Legendary Creature — Angel",
          "oracle_text": "Flash\nFlying, vigilance\nWhen Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.\nWhen a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.",
          "colors": [
            "W"
          ],
          "power": "4",
          "toughness": "4",
          "artist": "James Ryman",
          "artist_id": "3852bbc9-11c0-4fe3-8722-a06ad7e2bcc5",
          "illustration_id": "f90dd248-e671-4055-8031-c0f9938132ee",
          "image_uris": {
            "small": "https://c1.scryfall.com/file/scryfall-cards/small/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "normal": "https://c1.scryfall.com/file/scryfall-cards/normal/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "large": "https://c1.scryfall.com/file/scryfall-cards/large/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "png": "https://c1.scryfall.com/file/scryfall-cards/png/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.png?1576383667",
            "art_crop": "https://c1.scryfall.com/file/scryfall-cards/art_crop/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "border_crop": "https://c1.scryfall.com/file/scryfall-cards/border_crop/front/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667"
          }
        },
        {
          "object": "card_face",
          "name": "Avacyn, the Purifier",
          "mana_cost": "",
          "type_line": "Legendary Creature — Angel",
          "oracle_text": "Flying\nWhen this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.",
          "colors": [
            "R"
          ],
          "color_indicator": [
            "R"
          ],
          "power": "6",
          "toughness": "5",
          "flavor_text": "\"Wings that once bore hope are now stained with blood. She is our guardian no longer.\"\n—Grete, cathar apostate",
          "artist": "James Ryman",
          "artist_id": "3852bbc9-11c0-4fe3-8722-a06ad7e2bcc5",
          "illustration_id": "7c028c56-e6d0-4ce9-aea9-994ba128245f",
          "image_uris": {
            "small": "https://c1.scryfall.com/file/scryfall-cards/small/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "normal": "https://c1.scryfall.com/file/scryfall-cards/normal/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "large": "https://c1.scryfall.com/file/scryfall-cards/large/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "png": "https://c1.scryfall.com/file/scryfall-cards/png/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.png?1576383667",
            "art_crop": "https://c1.scryfall.com/file/scryfall-cards/art_crop/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667",
            "border_crop": "https://c1.scryfall.com/file/scryfall-cards/border_crop/back/a/e/ae155ee2-008f-4dc6-82bf-476be7baa224.jpg?1576383667"
          }
        }
      ],
      "legalities": {
        "standard": "not_legal",
        "future": "not_legal",
        "historic": "not_legal",
        "gladiator": "not_legal",
        "pioneer": "legal",
        "modern": "legal",
        "legacy": "legal",
        "pauper": "not_legal",
        "vintage": "legal",
        "penny": "not_legal",
        "commander": "legal",
        "brawl": "not_legal",
        "duel": "legal",
        "oldschool": "not_legal",
        "premodern": "not_legal"
      },
      "games": [
        "paper",
        "mtgo"
      ],
      "reserved": false,
      "foil": true,
      "nonfoil": true,
      "oversized": false,
      "promo": false,
      "reprint": false,
      "variation": false,
      "set": "soi",
      "set_name": "Shadows over Innistrad",
      "set_type": "expansion",
      "set_uri": "https://api.scryfall.com/sets/5e914d7e-c1e9-446c-a33d-d093c02b2743",
      "set_search_uri": "https://api.scryfall.com/cards/search?order=set&q=e%3Asoi&unique=prints",
      "scryfall_set_uri": "https://scryfall.com/sets/soi?utm_source=api",
      "rulings_uri": "https://api.scryfall.com/cards/ae155ee2-008f-4dc6-82bf-476be7baa224/rulings",
      "prints_search_uri": "https://api.scryfall.com/cards/search?order=released&q=oracleid%3A432b37a5-d32a-4b78-91ab-860aa026b7cc&unique=prints",
      "collector_number": "5",
      "digital": false,
      "rarity": "mythic",
      "card_back_id": "0aeebaf5-8c7d-4636-9e82-8c27447861f7",
      "artist": "James Ryman",
      "artist_ids": [
        "3852bbc9-11c0-4fe3-8722-a06ad7e2bcc5"
      ],
      "border_color": "black",
      "frame": "2015",
      "frame_effects": [
        "sunmoondfc"
      ],
      "full_art": false,
      "textless": false,
      "booster": true,
      "story_spotlight": false,
      "edhrec_rank": 3068,
      "prices": {
        "usd": "5.68",
        "usd_foil": "21.20",
        "eur": "4.90",
        "eur_foil": "9.00",
        "tix": "0.11"
      },
      "related_uris": {
        "gatherer": "https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=409741",
        "tcgplayer_decks": "https://decks.tcgplayer.com/magic/deck/search?contains=Archangel+Avacyn&page=1&utm_campaign=affiliate&utm_medium=api&utm_source=scryfall",
        "edhrec": "https://edhrec.com/route/?cc=Archangel+Avacyn",
        "mtgtop8": "https://mtgtop8.com/search?MD_check=1&SB_check=1&cards=Archangel+Avacyn"
      },
      "purchase_uris": {
        "tcgplayer": "https://shop.tcgplayer.com/product/productsearch?id=114848&utm_campaign=affiliate&utm_medium=api&utm_source=scryfall",
        "cardmarket": "https://www.cardmarket.com/en/Magic/Products/Search?referrer=scryfall&searchString=Archangel+Avacyn&utm_campaign=card_prices&utm_medium=text&utm_source=scryfall",
        "cardhoarder": "https://www.cardhoarder.com/cards/59766?affiliate_id=scryfall&ref=card-profile&utm_campaign=affiliate&utm_medium=card&utm_source=scryfall"
      }
    }        
    """.trimIndent())!!


}