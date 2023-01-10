@echo off

REM --------------------------------------------------
REM Monster Trading Cards Game
REM --------------------------------------------------
title Monster Trading Cards Game
echo Own CURL Testing for Monster Trading Cards Game
echo.

REM --------------------------------------------------
echo 1) Create Users (Registration)
REM Create User
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"test1\", \"Password\":\"test\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"test2\", \"Password\":\"test\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"admin\", \"Password\":\"test\"}"
echo.

echo should fail:
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"test1\", \"Password\":\"egal\"}"
echo.
curl -X POST http://localhost:10001/users --header "Content-Type: application/json" -d "{\"Username\":\"test2\", \"Password\":\"egal\"}"
echo. 
echo.

REM --------------------------------------------------
echo 2) Login Users
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"test1\", \"Password\":\"test\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"test2\", \"Password\":\"test\"}"
echo.
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"admin\", \"Password\":\"test\"}"
echo.

echo should fail:
curl -X POST http://localhost:10001/sessions --header "Content-Type: application/json" -d "{\"Username\":\"doesn't exist\", \"Password\":\"\"}"
echo.
echo.

REM --------------------------------------------------
echo 3) create packages (done by "admin")
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"dfdd758f-649c-40f9-ba3a-8657f4b3439f\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}]"
echo.																																																																																		 				    
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"644808c2-f87a-4600-b313-122b02322fd5\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"4a2757d6-b1c3-47ac-b9a3-91deab093531\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"91a6471b-1426-43f6-ad65-6fc473e16f9f\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"f8043c23-1534-4487-b66b-238e0c3c39b5\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}]"
echo.																																																																																		 				    
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"b017ee50-1c14-44e2-bfd6-2c0c5653a37c\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"d04b736a-e874-4137-b191-638e0ff3b4e7\", \"Name\":\"WaterElf\", \"Damage\": 10.0}, {\"Id\":\"88221cfe-1f84-41b9-8152-8e36c6a354de\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"1d3f175b-c067-4359-989d-96562bfa382c\", \"Name\":\"WaterTroll\", \"Damage\": 10.0}, {\"Id\":\"171f6076-4eb5-4a7d-b3f2-2d650cc3d237\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}]"
echo.																																																																																		 				    
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8\", \"Name\":\"WaterGoblin\", \"Damage\": 10.0}, {\"Id\":\"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"Name\":\"WaterElf\", \"Damage\": 10.0}, {\"Id\":\"55ef46c4-016c-4168-bc43-6b9b1e86414f\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}, {\"Id\":\"f3fad0f2-a1af-45df-b80d-2e48825773d9\", \"Name\":\"WaterTroll\", \"Damage\": 10.0}, {\"Id\":\"8c20639d-6400-4534-bd0f-ae563f11f57a\", \"Name\":\"WaterSpell\", \"Damage\": 10.0}]"
echo.																																																																																		 				    
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"Name\":\"FireGoblin\", \"Damage\": 10.0}, {\"Id\":\"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"Name\":\"FireElf\", \"Damage\": 10.0}, {\"Id\":\"2c98cd06-518b-464c-b911-8d787216cddd\", \"Name\":\"FireSpell\", \"Damage\": 10.0}, {\"Id\":\"951e886a-0fbf-425d-8df5-af2ee4830d85\", \"Name\":\"FireTroll\", \"Damage\": 10.0}, {\"Id\":\"dcd93250-25a7-4dca-85da-cad2789f7198\", \"Name\":\"FireSpell\", \"Damage\": 10.0}]"
echo.																																																																																		 				    
curl -X POST http://localhost:10001/packages --header "Content-Type: application/json" --header "Authorization: Basic admin-mtcgToken" -d "[{\"Id\":\"b2237eca-0271-43bd-87f6-b22f70d42ca4\", \"Name\":\"FireGoblin\", \"Damage\": 10.0}, {\"Id\":\"9e8238a4-8a7a-487f-9f7d-a8c97899eb48\", \"Name\":\"FireElf\", \"Damage\": 10.0}, {\"Id\":\"d60e23cf-2238-4d49-844f-c7589ee5342e\", \"Name\":\"FireSpell\", \"Damage\": 10.0}, {\"Id\":\"fc305a7a-36f7-4d30-ad27-462ca0445649\", \"Name\":\"FireTroll\", \"Damage\": 10.0}, {\"Id\":\"84d276ee-21ec-4171-a509-c1b88162831c\", \"Name\":\"FireSpell\", \"Damage\": 10.0}]"
echo.
echo.

REM --------------------------------------------------
echo 4) acquire packages test1
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d ""
echo.
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d ""
echo.
echo should fail (no money):
curl -X POST http://localhost:10001/transactions/packages --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d ""
echo.
echo.

REM --------------------------------------------------
echo 5) show all acquired cards test1
curl -X GET http://localhost:10001/cards --header "Authorization: Basic test2-mtcgToken"
echo should fail (no token)
curl -X GET http://localhost:10001/cards 
echo.
echo.

REM --------------------------------------------------
echo 6) show all acquired cards test2
curl -X GET http://localhost:10001/cards --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 7) show unconfigured deck
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 8) configure deck
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\"]"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "[\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"2c98cd06-518b-464c-b911-8d787216cddd\", \"951e886a-0fbf-425d-8df5-af2ee4830d85\"]"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test2-mtcgToken"
echo.
echo.
echo should fail and show original from before:
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "[\"845f0dc7-37d0-426e-994e-43fc3ac83c08\", \"99f8f8dc-e25e-4a95-aa2c-782823f36e2a\", \"e85e3976-7c86-4d06-9a80-641c2019a79f\", \"1cb6ab86-bdb2-47e5-b6e4-68c5ab389334\"]"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test2-mtcgToken"
echo.
echo.
echo should fail ... only 3 cards set
curl -X PUT http://localhost:10001/deck --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "[\"d7d0cb94-2cbf-4f97-8ccf-9933dc5354b8\", \"44c82fbc-ef6d-44ab-8c7a-9fb19a0e7c6e\", \"2c98cd06-518b-464c-b911-8d787216cddd\"]"
echo.


REM --------------------------------------------------
echo 9) show configured deck 
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/deck --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 10) show configured deck different representation
echo test1-X GET http://localhost:10001/deck?format=plain --header "Authorization: Basic test1-mtcgToken"
echo.
echo.
echo test2 -X GET http://localhost:10001/deck?format=plain --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 11) edit user data
echo.
curl -X GET http://localhost:10001/users/test1 --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/test2 --header "Authorization: Basic test2-mtcgToken"
echo.
curl -X PUT http://localhost:10001/users/test1 --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "{\"Name\": \"test1\",  \"Bio\": \"me playin...\", \"Image\": \":-)\"}"
echo.
curl -X PUT http://localhost:10001/users/test2 --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "{\"Name\": \"test2\", \"Bio\": \"me codin...\",  \"Image\": \":-D\"}"
echo.
curl -X GET http://localhost:10001/users/test1 --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/test2 --header "Authorization: Basic test2-mtcgToken"
echo.
echo.
echo should fail:
curl -X GET http://localhost:10001/users/test2 --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/users/test1 --header "Authorization: Basic test2-mtcgToken"
echo.
curl -X PUT http://localhost:10001/users/test1 --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "{\"Name\": \"Hoax\", \"Bio\": \"me playin...\", \"Image\": \":-)\"}"
echo.
curl -X PUT http://localhost:10001/users/test2 --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "{\"Name\": \"Hoax\", \"Bio\": \"me codin...\",  \"Image\": \":-D\"}"
echo.
curl -X GET http://localhost:10001/users/test3  --header "Authorization: Basic test1-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 12) stats
curl -X GET http://localhost:10001/stats --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/stats --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 13) scoreboard
curl -X GET http://localhost:10001/score --header "Authorization: Basic test1-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 14) battle
start /b "test1 battle" curl -X POST http://localhost:10001/battles --header "Authorization: Basic test1-mtcgToken"
ping localhost -n 2 >NUL 1>NUL
start /b "test2 battle" curl -X POST http://localhost:10001/battles --header "Authorization: Basic test2-mtcgToken"
ping localhost -n 10 >NUL 2>NUL

REM --------------------------------------------------
echo 15) Stats 
echo test1
curl -X GET http://localhost:10001/stats --header "Authorization: Basic test1-mtcgToken"
echo.
echo test2
curl -X GET http://localhost:10001/stats --header "Authorization: Basic test2-mtcgToken"
echo.
echo should fail:
curl -X GET http://localhost:10001/stats
echo.
echo.

REM --------------------------------------------------
echo 19) scoreboard
curl -X GET http://localhost:10001/score --header "Authorization: Basic test1-mtcgToken"
echo.
echo should fail:
curl -X GET http://localhost:10001/score
echo.
echo.

REM --------------------------------------------------
echo 16) trade
echo check trading deals
curl -X GET http://localhost:10001/tradings --header "Authorization: Basic test1-mtcgToken"
echo.
echo create trading deal
curl -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"Type\": \"monster\", \"MinimumDamage\": 15}"
echo.
echo check trading deals
curl -X GET http://localhost:10001/tradings --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/tradings --header "Authorization: Basic test2-mtcgToken"
echo.
echo delete trading deals
curl -X DELETE http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Authorization: Basic test1-mtcgToken"
echo should fail:
curl -X DELETE http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Authorization: Basic test2-mtcgToken"
echo.
echo.

REM --------------------------------------------------
echo 17) check trading deals
curl -X GET http://localhost:10001/tradings  --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X POST http://localhost:10001/tradings --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "{\"Id\": \"6cd85277-4590-49d4-b0cf-ba0a921faad0\", \"CardToTrade\": \"65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5\", \"Type\": \"monster\", \"MinimumDamage\": 10}"
echo.
echo check trading deals
curl -X GET http://localhost:10001/tradings  --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/tradings  --header "Authorization: Basic test2-mtcgToken"
echo.
echo try to trade with yourself (should fail)
curl -X POST http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Content-Type: application/json" --header "Authorization: Basic test1-mtcgToken" -d "\"4ec8b269-0dfa-4f97-809a-2c63fe2a0025\""
echo.
echo try to trade 
echo.
curl -X POST http://localhost:10001/tradings/6cd85277-4590-49d4-b0cf-ba0a921faad0 --header "Content-Type: application/json" --header "Authorization: Basic test2-mtcgToken" -d "\"fc305a7a-36f7-4d30-ad27-462ca0445649\""
echo.
curl -X GET http://localhost:10001/tradings --header "Authorization: Basic test1-mtcgToken"
echo.
curl -X GET http://localhost:10001/tradings --header "Authorization: Basic test2-mtcgToken"
echo.

REM --------------------------------------------------
echo end...

REM this is approx a sleep 
ping localhost -n 100 >NUL 2>NUL
@echo on
