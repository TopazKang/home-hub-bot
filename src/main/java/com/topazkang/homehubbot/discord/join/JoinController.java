package com.topazkang.homehubbot.discord.join;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/{token}")
    public ResponseEntity<String> join(@PathVariable String token, HttpServletRequest request) {
        String ip = request.getRemoteAddr();

        joinService.processJoinLink(token, ip);

        String html = """
                        <!DOCTYPE html>
                        <html>
                        <head>
                            <meta charset="UTF-8">
                            <title>HomeHub</title>
                        </head>
                        <body>
                            <h2>접속 요청이 완료되었습니다.</h2>
                            <p>이 창은 닫으셔도 됩니다.</p>
                
                            <script>
                                setTimeout(() => {
                                    window.close();
                                }, 1500);
                            </script>
                        </body>
                        </html>
                        """;

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

}
