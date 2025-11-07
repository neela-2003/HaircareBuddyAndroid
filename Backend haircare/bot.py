import os
import json
from http.server import BaseHTTPRequestHandler, HTTPServer
import google.generativeai as genai

# Handler for HTTP requests
class MyHandler(BaseHTTPRequestHandler):
    def do_POST(self):
        try:
            # Step 1: Read and decode the incoming data
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            received_data = json.loads(post_data.decode('utf-8'))
            
            print("Received userdata:", received_data)

            # Step 2: Extract user answers
            userdata = received_data.get("userdata", {})
            if not userdata:
                self.send_error(400, "Missing 'userdata' in request")
                return
            
            # Step 3: Configure GenAI API (replace with your actual API key)
            genai.configure(api_key="AIzaSyDsDEmkPo9xQgLyPltFdzvf6KBBV5PVb1g")

            # Define the model configuration
            generation_config = {
                "temperature": 0.7,
                "top_p": 0.9,
                "top_k": 40,
                "max_output_tokens": 500,
                "response_mime_type": "text/plain",
            }

            # Instantiate the model
            model = genai.GenerativeModel(
                model_name="gemini-1.5-flash",
                generation_config=generation_config,
                system_instruction="Act as a haircare bot and provide personalized tips based on user input. ** maximum 500 words response ** ",
            )

            # Prepare input for the AI
            chat_session = model.start_chat(history=[])
            input_message = f"User answers: {json.dumps(userdata)}. Provide suggestions and tips."

            # Step 4: Send input to the AI and get the response
            response = chat_session.send_message(input_message)
            ai_reply = response.text
            print("AI Response:", ai_reply)

            # Step 5: Prepare the response to PHP
            response_data = {
                "reply": ai_reply,
                "Username": "Haircare Bot"
            }
            response_json = json.dumps(response_data)

            # Step 6: Send the response back to PHP
            self.send_response(200)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
            self.wfile.write(response_json.encode('utf-8'))

        except Exception as e:
            print("Error occurred:", e)
            self.send_response(500)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
            error_response = {"error": "Internal server error"}
            self.wfile.write(json.dumps(error_response).encode('utf-8'))

# Start the server
if __name__ == "__main__":
    server_address = ('', 8000)  # Run on localhost, port 8000
    httpd = HTTPServer(server_address, MyHandler)
    print("Python AI bot server is running on port 8000...")
    httpd.serve_forever()